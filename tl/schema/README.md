# TL schema (vendored)

Источник истины для слоя и будущей кодогенерации. В рантайме не скачивается.

| файл | что это | слой |
| --- | --- | --- |
| `api.tl` | Telegram API (tdesktop) | **229** (`// LAYER 229` в конце) |
| `mtproto.tl` | ядро MTProto: handshake, контейнер, `rpc_result` | без номера, меняется редко |

Дефолт `TelegramClient(layer = API_LAYER)` обязан совпадать с `// LAYER` в `api.tl`. Другой слой — другой `api.tl` + перегенерация типов, плюс `TelegramClient(layer = N)`.

Генератор: из корня репо

```
./gradlew generateTl
```

(`:tl:generateTl` — то же). Пишет в `tl/build/generated/tl/kotlin/` (не в git). Перед компиляцией `:tl` задача вызывается сама. Смена слоя — заменить `api.tl`, выровнять `API_LAYER` или `TelegramClient(layer = N)`, снова `generateTl`. Подробнее в корневом README, секция **Generate TL types**.
