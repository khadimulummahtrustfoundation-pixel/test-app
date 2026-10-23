# FocusGuard — Android prototype

FocusGuard is an experimental Android Accessibility Service prototype intended to reduce Reels and full-screen video viewing in Facebook, Facebook Lite, Instagram, and Instagram Lite while leaving ordinary feeds and recognizable Stories usable.

## Important limitations

- Social apps do not provide a stable public API that identifies every Reel or fullscreen video.
- This prototype uses visible UI text and node-size heuristics. It may miss screens or incorrectly press Back.
- Story exemption depends on the app exposing recognizable Story labels to Android Accessibility.
- Test with your own device and current app versions before relying on it. Do not market it as guaranteed protection yet.
- Accessibility permission is powerful. Explain the purpose clearly to users and request only the access needed for the feature.

## Build APK on GitHub

1. Create a new GitHub repository.
2. Upload the contents of this ZIP to the repository root (not the ZIP file itself).
3. Commit the files to the `main` branch.
4. Open **Actions**. If prompted, enable workflows.
5. Select **Build Android APK**, then click **Run workflow** (or wait for the push build).
6. Open the completed workflow run and download the `focusguard-debug-apk` artifact.
7. Extract the downloaded artifact ZIP; install `app-debug.apk` on an Android test phone.

## Enable service

Open FocusGuard, tap **Open Accessibility Settings**, then enable **FocusGuard screen protection**. Android will show a system permission warning. Review it before enabling.

## Package details

- Application ID: `com.focusguard`
- Minimum Android version: Android 8.0 (API 26)
- Version: `0.1.0` (prototype)
