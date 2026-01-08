# APK File Location Guide

This document explains how to find the Music App APK files.

## 📦 How to Find APK Files

### 1. Download from GitHub Releases (Easiest Method) ⭐

The latest built APK files are available on the GitHub Releases page:

**Download Link:** [https://github.com/kimrasng/music_app/releases](https://github.com/kimrasng/music_app/releases)

- Each release includes an `app-release.apk` file
- Latest release: [build-6](https://github.com/kimrasng/music_app/releases/tag/build-6)
- Download the APK file from the "Assets" section of the release page

### 2. Download from GitHub Actions Artifacts

APK files are uploaded as artifacts whenever the GitHub Actions workflow runs:

1. Go to the [Actions tab](https://github.com/kimrasng/music_app/actions)
2. Click on a successfully completed "Android CI" workflow run
3. Download `release-apk` from the "Artifacts" section at the bottom of the page

> **Note:** Artifacts are automatically deleted after 90 days.

### 3. Build Locally

To build the APK locally:

```bash
# Clone the repository
git clone https://github.com/kimrasng/music_app.git
cd music_app

# Build Release APK
./gradlew assembleRelease
```

After the build completes, the APK file will be located at:
```
app/build/outputs/apk/release/app-release.apk
```

## 📋 APK Build Information

- **Build Method:** Automated GitHub Actions build
- **Trigger:** Automatically runs on every push to the `main` branch
- **JDK Version:** 17
- **Build Type:** Release (unsigned APK)
- **Workflow File:** `.github/workflows/android.yml`

## 🔧 CI/CD Pipeline

The project uses the following automated build process:

1. **Build APK:** Builds the release APK and uploads it as an artifact
2. **Create GitHub Release:** Automatically creates a GitHub Release and attaches the APK file

## ⚠️ Important Notes

- The current APK is **unsigned**
- A separate signing process is required to upload to the Google Play Store
- For testing purposes, you need to allow "Unknown sources" installation on your Android device

## 🔗 Related Links

- [GitHub Releases](https://github.com/kimrasng/music_app/releases)
- [GitHub Actions](https://github.com/kimrasng/music_app/actions)
- [Android CI Workflow](.github/workflows/android.yml)
