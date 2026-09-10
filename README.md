
# IntoBeta Live - Open Source IPTV Player (Android Native)

Xtream Codes API based IPTV Player. 100% Android Native Kotlin.

## Features
- Xtream Login (Server, Username, Password)
- Live Categories + Streams
- VOD Support
- ExoPlayer (Media3) HLS .m3u8
- GitHub Actions Auto Release APK

## Build Locally
./gradlew assembleRelease

## Auto Release via GitHub
```bash
git tag v1.0.0
git push origin v1.0.0
```
Go to Releases -> APK ready.

## API Used
player_api.php?action=get_live_categories
player_api.php?action=get_live_streams
live/USER/PASS/ID.m3u8

Package: com.intobeta.live
