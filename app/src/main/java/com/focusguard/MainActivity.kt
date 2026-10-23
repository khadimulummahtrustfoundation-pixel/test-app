package com.focusguard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pad = (20 * resources.displayMetrics.density).toInt()
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(pad, pad * 2, pad, pad)
        }

        val title = TextView(this).apply {
            text = "FocusGuard"
            textSize = 30f
            setTextColor(0xFF16834A.toInt())
            gravity = Gravity.CENTER
        }
        val intro = TextView(this).apply {
            text = "Reduce Reels and full-screen video scrolling. Keep using feeds, posts, and Stories.\\n\\nThis is an experimental prototype: social apps may change their screen labels, so blocking will not be perfect. Stories are intentionally exempt when recognizable."
            textSize = 16f
            setTextColor(0xFF333333.toInt())
            setPadding(0, pad, 0, pad)
        }
        val enable = Button(this).apply {
            text = "Open Accessibility Settings"
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
        }
        val note = TextView(this).apply {
            text = "Enable “FocusGuard screen protection” in Accessibility settings. Android will show a system disclosure for this permission."
            textSize = 14f
            setPadding(0, pad, 0, 0)
        }
        layout.addView(title, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layout.addView(intro, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layout.addView(enable, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layout.addView(note, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        setContentView(layout)
    }
}
