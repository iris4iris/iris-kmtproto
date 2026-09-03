# Iris kMTProto

Kotlin Multiplatform MTProto client (`iris.kmtproto`). First target: **JVM**. Session is in-memory only — no disk, no local message database. Request/response API, bot login next.

```kotlin
import iris.kmtproto.client.TelegramClient

val client = TelegramClient(apiId, apiHash)
client.connect()          // TCP obfuscated-intermediate + auth_key handshake
val pong = client.ping()  // first encrypted RPC
```

## Layout

One Gradle build, two modules:

```
:tl     schema/api.tl, TL codec, generator → build/generated (not in git)
:core   handshake, client, echo bot — depends on :tl
```

```
./gradlew generateTl          # :tl, runs automatically before compile
./gradlew :tl:jvmTest
./gradlew :core:jvmTest
./gradlew :core:jvmRun        # echo bot
```

IntelliJ: open the repo root. Gradle runner. Echo bot main: `iris.kmtproto.example.EchoBotMainKt` (`:core:jvmRun`).

`HandshakeLiveTest.handshakeAndPingDc2` does not need a real `apiId`.

`loginBotIfConfigured` / `sendMessageIfConfigured` / `updatesCatchupIfConfigured` run only when env is set:

```
TELEGRAM_API_ID
TELEGRAM_API_HASH
TELEGRAM_BOT_TOKEN
TELEGRAM_CHAT_ID   # for sendMessage
```

PowerShell:

```
$env:TELEGRAM_API_ID="…"
$env:TELEGRAM_API_HASH="…"
$env:TELEGRAM_BOT_TOKEN="…"
.\gradlew.bat :core:jvmTest --tests iris.kmtproto.HandshakeLiveTest.loginBotIfConfigured
```

Apache-2.0
