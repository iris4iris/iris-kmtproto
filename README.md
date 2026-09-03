# Iris kMTProto

Kotlin Multiplatform MTProto client (`iris.kmtproto`). First target: **JVM**. Session is in-memory only — no disk, no local message database.

```kotlin
import iris.kmtproto.client.TelegramClient

val client = TelegramClient(apiId, apiHash)
client.connect()          // TCP obfuscated-intermediate + auth_key handshake
val pong = client.ping().await()  // Deferred — coroutine Future
```

SOCKS5 (optional):

```kotlin
import iris.kmtproto.transport.Proxy

TelegramClient(apiId, apiHash, proxy = Proxy.Socks5("127.0.0.1", 1080))
```

User login (SMS + optional 2FA):

```kotlin
val api = UserApi(client)
val sent = api.auth.sendCode("+79990000000").await() as AuthSentCodeCtor
try {
    api.auth.signIn("+79990000000", sent.phoneCodeHash, codeFromSms).await()
} catch (e: SessionPasswordNeeded) {
    api.auth.checkPassword(cloudPassword).await()
}
```

Bot API-shaped adapter (still MTProto underneath):

```kotlin
val bot = BotApi(apiId, apiHash)
bot.client.connect()
bot.login(token).await()
bot.sendMessage(chatId, "hi").await()
bot.incomingMessages().collect { bot.sendMessage(it.chatId, it.text) } // fire-and-forget Deferred
```

Save `client.session()` and pass it to the next `connect(session = …)` so you do not send SMS again.
## Layout

One Gradle build, two modules:

```
:tl     schema/api.tl, TL codec, generator → tl/build/generated (not in git)
:core   handshake, client, echo bot — depends on :tl
```

## Generate TL types

Types and constructors are **not** in git. They are generated from `tl/schema/api.tl`.

**Command** (from repo root):

```
./gradlew generateTl
```

Windows: `.\gradlew.bat generateTl`  
То же самое: `./gradlew :tl:generateTl`

Пишет в `tl/build/generated/tl/kotlin/iris/kmtproto/tl/gen/`.  
`compileKotlin` в `:tl` вызывает генерацию сам, если схемы или генератора ещё нет в кэше. Явный `generateTl` нужен после смены `api.tl`.

### Сменить слой

1. Положить новый `api.tl` в `tl/schema/` (например с [tdesktop](https://github.com/telegramdesktop/tdesktop) / `telegram_api.tl`). `mtproto.tl` трогать не нужно, пока не менялся handshake.
2. В конце файла должна быть строка `// LAYER N` — это номер слоя.
3. Дефолт клиента: `API_LAYER` в `tl/src/commonMain/kotlin/iris/kmtproto/tl/Api.kt` должен совпасть с этим `N`. Иначе `TelegramClient(layer = N)` при создании.
4. Запустить:

```
./gradlew generateTl
./gradlew :tl:jvmTest :core:jvmTest
```

5. Не коммить `tl/build/`. В git только схема и генератор.

## Build / run

```
./gradlew generateTl          # :tl → build/generated
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

User login is not a live test (it would send SMS). Call `sendCode` / `signIn` / `checkPassword` from your app.

PowerShell:

```
$env:TELEGRAM_API_ID="…"
$env:TELEGRAM_API_HASH="…"
$env:TELEGRAM_BOT_TOKEN="…"
.\gradlew.bat :core:jvmTest --tests iris.kmtproto.HandshakeLiveTest.loginBotIfConfigured
```

Apache-2.0
