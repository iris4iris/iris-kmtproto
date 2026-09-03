# Iris kMTProto

Kotlin Multiplatform MTProto client (`iris.kmtproto`). First target: **JVM**. Session is in-memory only — no disk, no local message database. Request/response API, bot login next.

```kotlin
import iris.kmtproto.client.TelegramClient

val client = TelegramClient(apiId, apiHash)
client.connect()          // TCP obfuscated-intermediate + auth_key handshake
val pong = client.ping()  // first encrypted RPC
```

## Layout

```
kmtproto/
  src/commonMain/kotlin/iris/kmtproto   TL runtime, AES-IGE, RSA_PAD, handshake, session
  src/jvmMain/kotlin/iris/kmtproto      JDK crypto, obfuscated TCP
  src/jvmTest/kotlin/iris/kmtproto      round-trip tests + live DC2 ping
```

## Status

- [x] TL reader/writer + MTProto constructors
- [x] AES-256-IGE, RSA_PAD 2.0, pq factorize
- [x] TCP obfuscated intermediate
- [x] `createAuthKey` handshake
- [x] encrypted `ping` / `pong`
- [ ] `invokeWithLayer` + `auth.importBotAuthorization`
- [ ] files, updates engine, user SMS/SRP
- [ ] Android / Native actuals (same commonMain)

## Build

```
./gradlew jvmTest
```

Apache-2.0
