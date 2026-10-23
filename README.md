# FocusGuard (experimental Android prototype)

FocusGuard is an experimental Android AccessibilityService prototype intended to help reduce accidental entry into Reels and likely full-screen video screens in Facebook, Facebook Lite, Instagram, and Instagram Lite.

## Build APK with GitHub Actions

1. Upload the **contents** of this folder to the root of a GitHub repository, preserving the `.github/workflows/android.yml` path.
2. Open the repository's **Actions** tab.
3. Choose **Build Android APK** and select **Run workflow** (or push to `main`).
4. When the run succeeds, open the run and download the `focusguard-debug-apk` artifact. Extract it to get `app-debug.apk`.
5. Install the APK on an Android test device. Open FocusGuard and enable **FocusGuard screen protection** in Android Accessibility settings.

The workflow pins Gradle 8.9 because this project uses Android Gradle Plugin 8.7.3, which is compatible with Gradle 8.9. If a build fails, open the **Build APK** log and inspect the first `What went wrong:` or `e:` error. Failed runs also attempt to upload build reports as `focusguard-build-reports`.

## Important limitations

- This is a prototype, not a finished or verified app. The APK must be built and tested on a device before relying on it.
- Facebook and Instagram change their interface frequently. Accessibility labels may be missing, translated, or different between app versions; Reels/full-screen detection can therefore have false positives or false negatives.
- Story detection is heuristic and may accidentally exempt other screens or fail to recognize a Story. This prototype cannot guarantee that every Reel or full-screen video will be blocked while every Story and feed video remains available.
- Android may display an accessibility-permission disclosure. Enable the service only if you understand and accept its behavior.
