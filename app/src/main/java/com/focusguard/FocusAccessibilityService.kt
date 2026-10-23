package com.focusguard

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.os.SystemClock

/**
 * Experimental UI heuristic only. Facebook/Instagram do not expose a stable public API
 * for identifying Reels or fullscreen players. This service avoids acting on recognizable
 * Story screens, and only presses Back when a strong Reel label or likely fullscreen video
 * is detected. False positives/negatives remain possible and must be tested on-device.
 */
class FocusAccessibilityService : AccessibilityService() {
    private val targetPackages = setOf(
        "com.facebook.katana",
        "com.facebook.lite",
        "com.instagram.android",
        "com.instagram.lite"
    )

    private var lastActionAt = 0L
    private var lastScreenSignature = ""
    private var repeatedSignatureCount = 0

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return
        val pkg = event.packageName?.toString() ?: return
        if (pkg !in targetPackages) return

        val root = rootInActiveWindow ?: return
        val texts = mutableListOf<String>()
        collectText(root, texts, 0)
        val joined = texts.joinToString(" ").lowercase()
        val signature = "$pkg|${joined.take(500)}"

        if (signature == lastScreenSignature) repeatedSignatureCount++
        else {
            lastScreenSignature = signature
            repeatedSignatureCount = 0
        }

        // Story viewing is explicitly exempt when recognizable.
        if (looksLikeStory(joined)) return

        val explicitReel = looksLikeReel(joined)
        val likelyFullscreenVideo = looksLikeFullscreenVideo(root, joined)

        // Strong Reel label, or a likely fullscreen player. Debounce Back to avoid loops.
        if (explicitReel || likelyFullscreenVideo) {
            val now = SystemClock.elapsedRealtime()
            if (now - lastActionAt > 1200) {
                lastActionAt = now
                performGlobalAction(GLOBAL_ACTION_BACK)
            }
        }
    }

    override fun onInterrupt() = Unit

    private fun collectText(node: AccessibilityNodeInfo?, out: MutableList<String>, depth: Int) {
        if (node == null || depth > 18 || out.size > 500) return
        node.text?.toString()?.takeIf { it.isNotBlank() }?.let { out.add(it) }
        node.contentDescription?.toString()?.takeIf { it.isNotBlank() }?.let { out.add(it) }
        for (i in 0 until node.childCount) {
            collectText(node.getChild(i), out, depth + 1)
        }
    }

    private fun looksLikeStory(text: String): Boolean {
        val markers = listOf(
            "your story", "add to story", "stories", "story",
            "স্টোরি", "আপনার স্টোরি", "গল্প"
        )
        return markers.any { text.contains(it) }
    }

    private fun looksLikeReel(text: String): Boolean {
        val markers = listOf(
            "reels", "reel", "রিলস", "রিল",
            "create reel", "reels video"
        )
        return markers.any { text.contains(it) }
    }

    private fun looksLikeFullscreenVideo(root: AccessibilityNodeInfo, text: String): Boolean {
        // Do not infer fullscreen from a generic video label alone; require a video-related
        // label plus a very large visible node. This is only a best-effort heuristic.
        val videoWords = listOf("video", "ভিডিও", "pause video", "play video", "ভিডিও বিরতি")
        if (videoWords.none { text.contains(it) }) return false
        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        return containsLargeNode(root, width, height, 0)
    }

    private fun containsLargeNode(
        node: AccessibilityNodeInfo?,
        screenWidth: Int,
        screenHeight: Int,
        depth: Int
    ): Boolean {
        if (node == null || depth > 16) return false
        val rect = android.graphics.Rect()
        node.getBoundsInScreen(rect)
        val area = rect.width().toLong() * rect.height().toLong()
        val screenArea = screenWidth.toLong() * screenHeight.toLong()
        if (screenArea > 0 && area > screenArea * 0.72 && rect.width() > screenWidth * 0.75) {
            return true
        }
        for (i in 0 until node.childCount) {
            if (containsLargeNode(node.getChild(i), screenWidth, screenHeight, depth + 1)) return true
        }
        return false
    }
}
