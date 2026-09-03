# TL schema (vendored)

Источник истины для слоя и будущей кодогенерации. В рантайме не скачивается.

| файл | что это | слой |
| --- | --- | --- |
| `api.tl` | Telegram API (tdesktop) | **229** (`// LAYER 229` в конце) |
| `mtproto.tl` | ядро MTProto: handshake, контейнер, `rpc_result` | без номера, меняется редко |

Дефолт `TelegramClient(layer = API_LAYER)` обязан совпадать с `// LAYER` в `api.tl`. Другой слой — другой `api.tl` + перегенерация типов, плюс `TelegramClient(layer = N)`.

Генератор: `./gradlew generateTl` (или `:tl:generateTl`) пишет в `tl/build/generated/tl/kotlin/` (не в git). `compileKotlin*` в `:tl` зависит от этой задачи. `TlRegistry` вызывает `registerGenerated()` на старте. Ручными остаются handshake/mtproto и `invokeWithLayer` / `initConnection`.
