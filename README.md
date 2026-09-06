# Iris kMTProto

Kotlin Multiplatform MTProto client (`iris.kmtproto`). First target: **JVM**. Session is in-memory only — no disk, no local message database. **0.x — API will break.**

```kotlin
import iris.kmtproto.client.TelegramClient

val client = TelegramClient(apiId, apiHash)
client.connect()          // TCP obfuscated-intermediate + auth_key handshake
val pong = client.ping()                 // suspend, ждёт pong
val pongLater = client.pingAsync()       // Deferred, результат потом
```

After `connect()` two TCP sessions share the auth_key: **updates** (subscribed) and **rpc** (`invokeWithoutUpdates`). File upload/download opens a third **media** socket and closes it after 30s idle.

SOCKS5 (optional):

```kotlin
import iris.kmtproto.transport.Proxy

TelegramClient(apiId, apiHash, proxy = Proxy.Socks5("127.0.0.1", 1080))
```

User login (SMS + optional 2FA):

```kotlin
import iris.kmtproto.api.user.UserApi

val api = UserApi(client)
val sent = api.auth.sendCode("+79990000000") as AuthSentCodeCtor
try {
    api.auth.signIn("+79990000000", sent.phoneCodeHash, codeFromSms)
} catch (e: SessionPasswordNeeded) {
    api.auth.checkPassword(cloudPassword)
}
```

Bot API-shaped adapter (still MTProto underneath):

```kotlin
val bot = BotApi(apiId, apiHash)
bot.client.connect()
bot.login(token)
bot.sendMessage(chatId, "hi") // RpcResponse: result / error
bot.incomingMessages().collect { launch { bot.sendMessage(it.chatId, it.text) } }
client.incomingUpdates().collect { upd -> /* UpdateNewMessage, UpdateUserStatus, … */ }
client.incomingMessages() // sugar: UpdateNewMessage / UpdateNewChannelMessage → MessageCtor

user.payments.getStarGifts()
user.payments.getUniqueStarGift("PlushPepe-42")
user.payments.getUniqueStarGiftValueInfo("https://t.me/nft/PlushPepe-42")
user.payments.getSavedStarGift(msgId)
user.contacts.resolveUsername("durov")
user.contacts.resolve(peerId) // Bot API id, access_hash from storage
bot.getChat("durov")
bot.getChat(-1002696504560L)
bot.getStarGifts()

val r = client.invoke(method) // RpcResponse: result or Telegram RpcError, no throw

user.messages.send(peerId, "hi")
user.messages.send(
    peerId,
    "hi",
    silent = true,
    noWebpage = true,
    replyTo = InputReplyToMessage(replyToMsgId = 42),
)
user.messages.sendAsync(peerId, "hi")  // Deferred
user.messages.sendPhoto(peerId, jpegBytes, caption = "hi", fileName = "cat.jpg")
bot.sendPhoto(chatId, jpegBytes, caption = "hi")
user.messages.sendVideo(peerId, mp4Bytes, caption = "clip", fileName = "clip.mp4")
bot.sendVideo(chatId, mp4Bytes)
user.messages.sendVoice(peerId, oggOpusBytes, duration = 3)
user.messages.sendVideoNote(peerId, mp4Bytes, duration = 5.0, length = 384)
user.messages.sendDocument(peerId, pdfBytes, fileName = "a.pdf")
user.messages.sendGif(peerId, mp4Bytes, fileName = "loop.mp4")
FileByteSource("clip.mp4").use { src ->
    user.messages.sendVideo(client.inputPeerFromId(peerId), src, fileName = "clip.mp4")
}

user.getMe()
user.messages.history(peerId, limit = 50)
user.messages.get(peerId, 1, 2, 3)
user.messages.edit(peerId, id, "edited")
user.messages.delete(peerId, intArrayOf(id))
user.messages.forward(toId = chatId, fromId = peerId, ids = intArrayOf(id))
user.messages.dialogs()
user.messages.read(peerId)
user.messages.download(peerId, id)
user.messages.search(peerId, "query")
user.messages.searchGlobal("query")
user.join("durov")
user.join("https://t.me/+invitehash")
user.join(channelId)
user.leave(channelId)
user.messages.typing(peerId)
user.messages.pin(peerId, id)
user.messages.unpin(peerId, id)
user.messages.unpinAll(peerId)
user.messages.react(peerId, id, "👍")
user.participants(channelId)
user.ban(channelId, userId)
user.kick(channelId, userId)
user.unban(channelId, userId)
user.restrict(channelId, userId, ChatBannedRights(untilDate = 0, sendMessages = true))
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
./gradlew :core:runEcho       # echo bot (BotApi)
./gradlew :core:runBotApi     # BotApi: login + send + listen
./gradlew :core:runUserApi    # UserApi: SMS login + send + listen
```

`:core` зависит от `:tl` как от проекта — Gradle обязан проверить, актуален ли jar. Это доли секунды (`UP-TO-DATE`), не 19 с.

JVM IGE: HotSpot AES-NI через внутренний `AESCrypt` (без JNI на каждый 16-байтный блок). Нужен флаг; без него — `Cipher` ECB.

```
--add-opens java.base/com.sun.crypto.provider=ALL-UNNAMED
```

`./gradlew :core:jvmTest` и `runEcho` / `runBotApi` / `runUserApi` уже передают его. IDEA Run: VM options → та же строка. В логе: `kmtproto [aes] AESCrypt (HotSpot AES-NI)` или `Cipher ECB fallback`.

Чтобы `:core:jvmTest` вообще не ставил в граф `:tl:*`, в `gradle.properties`:

```
tl.prebuilt=true
```

Сначала один раз `./gradlew :tl:jvmJar` (или `generateTl` + `:tl:jvmJar`). Дальше core берёт `tl/build/libs/tl-jvm-*.jar`. Схема/генератор поменялись — снова `:tl:jvmJar`. Снять флаг или `-Ptl.prebuilt=false`, если нужна живая связь модулей.

```
./gradlew :core:jvmTest --tests iris.kmtproto.InboundThroughputTest
```

`unwrap*` — только IGE+TL в том же процессе. `fakeDc*` — отдельная JVM с Fake DC (`FakeDcMain`) + клиент в тесте; так encrypt/GC сервера не делят кучу с unwrap. Packed = 32 `UpdateNewMessage` в одном `UpdatesCtor`.

`KMTPROTO_FAKE_DC_INPROCESS=1` — старый режим, сервер-тред в JVM теста.

Прогрев: 20 000 кадров (не входят в таблицу). `KMTPROTO_BENCH_WARMUP=0` — без него.

Fake DC не шифрует на горячем пути: IGE-кадры один раз пишутся в `build/fake-dc-cache/m{messages}-n{warmup+frames}.bin`, грузятся в RAM, после handshake весь блоб CTR'ится один раз и отдаётся в сокет. Packed 520 000 кадров ≈ 1.1 GiB; процессу нужно `-Xmx2g` (уже в `runFakeDc` и spawn из теста). Каталог: `KMTPROTO_FAKE_DC_CACHE`.

Два процесса вручную:

```
./gradlew :core:runFakeDc --args="--messages 32 --frames 500000"
./gradlew :core:runDcBench --args="--messages 32 --frames 500000"
```

`runDcBench` сам поднимает Fake DC. Подключиться к уже запущенному серверу: `--port --key --salt --session` из ready-строки.

```
KMTPROTO_BENCH_N=1000000
KMTPROTO_BENCH_DC_N=100000
KMTPROTO_BENCH_DC_N_FAT=20000
```

Mains:

- `iris.kmtproto.example.EchoBotMainKt`
- `iris.kmtproto.example.BotApiExampleMainKt`
- `iris.kmtproto.example.UserApiExampleMainKt`

`local.properties` or env:

```
TELEGRAM_API_ID
TELEGRAM_API_HASH
TELEGRAM_BOT_TOKEN     # bot examples
TELEGRAM_PHONE         # user example
TELEGRAM_CHAT_ID       # optional send
TELEGRAM_CODE          # optional, иначе stdin
TELEGRAM_2FA           # optional cloud password
```

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
