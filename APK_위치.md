# APK 파일 위치 안내

이 문서는 Music App의 APK 파일을 찾는 방법을 설명합니다.

## 📦 APK 파일을 찾는 방법

### 1. GitHub Releases에서 다운로드 (가장 쉬운 방법) ⭐

가장 최신의 빌드된 APK 파일은 GitHub Releases 페이지에서 다운로드할 수 있습니다:

**다운로드 링크:** [https://github.com/kimrasng/music_app/releases](https://github.com/kimrasng/music_app/releases)

- 각 릴리즈에는 `app-release.apk` 파일이 첨부되어 있습니다
- 최신 릴리즈: [build-6](https://github.com/kimrasng/music_app/releases/tag/build-6)
- 릴리즈 페이지의 "Assets" 섹션에서 APK 파일을 다운로드할 수 있습니다

### 2. GitHub Actions Artifacts에서 다운로드

GitHub Actions 워크플로우가 실행될 때마다 APK 파일이 artifact로 업로드됩니다:

1. [Actions 탭](https://github.com/kimrasng/music_app/actions)으로 이동
2. 성공적으로 완료된 "Android CI" 워크플로우 실행을 클릭
3. 페이지 하단의 "Artifacts" 섹션에서 `release-apk`를 다운로드

> **참고:** Artifacts는 90일 후에 자동으로 삭제됩니다.

### 3. 직접 빌드하기

로컬에서 직접 APK를 빌드하려면:

```bash
# 저장소 클론
git clone https://github.com/kimrasng/music_app.git
cd music_app

# Release APK 빌드
./gradlew assembleRelease
```

빌드가 완료되면 APK 파일은 다음 경로에 생성됩니다:
```
app/build/outputs/apk/release/app-release.apk
```

## 📋 APK 빌드 정보

- **빌드 방식:** GitHub Actions 자동 빌드
- **트리거:** `main` 브랜치에 push될 때마다 자동 실행
- **JDK 버전:** 17
- **빌드 유형:** Release (서명되지 않은 APK)
- **워크플로우 파일:** `.github/workflows/android.yml`

## 🔧 CI/CD 파이프라인

현재 프로젝트는 다음과 같은 자동화된 빌드 프로세스를 사용합니다:

1. **Build APK:** Release APK를 빌드하고 artifact로 업로드
2. **Create GitHub Release:** 자동으로 GitHub Release를 생성하고 APK 파일을 첨부

## ⚠️ 주의사항

- 현재 APK는 **서명되지 않은 상태**입니다
- Google Play Store에 업로드하려면 별도의 서명 과정이 필요합니다
- 테스트 목적으로 사용 시 Android 기기에서 "알 수 없는 출처" 설치를 허용해야 합니다

## 🔗 관련 링크

- [GitHub Releases](https://github.com/kimrasng/music_app/releases)
- [GitHub Actions](https://github.com/kimrasng/music_app/actions)
- [Android CI Workflow](.github/workflows/android.yml)
