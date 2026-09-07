package iris.kmtproto

internal fun concat(vararg parts: ByteArray): ByteArray {
    val out = ByteArray(parts.sumOf { it.size })
    var offset = 0
    for (part in parts) {
        part.copyInto(out, offset)
        offset += part.size
    }
    return out
}

internal fun ByteArray.xor(other: ByteArray): ByteArray {
    require(size == other.size)
    return ByteArray(size) { (this[it].toInt() xor other[it].toInt()).toByte() }
}

internal fun ByteArray.toHex(): String = joinToString("") { b ->
    val v = b.toInt() and 0xff
    val hi = "0123456789abcdef"[v shr 4]
    val lo = "0123456789abcdef"[v and 0xf]
    "$hi$lo"
}

internal fun String.hexToBytes(): ByteArray {
    val clean = lowercase().replace(" ", "")
    require(clean.length % 2 == 0) { "odd hex length" }
    return ByteArray(clean.length / 2) { i ->
        ((clean[i * 2].digitToInt(16) shl 4) or clean[i * 2 + 1].digitToInt(16)).toByte()
    }
}

internal fun Int.toLeBytes(): ByteArray = byteArrayOf(
    toByte(),
    (this shr 8).toByte(),
    (this shr 16).toByte(),
    (this shr 24).toByte(),
)

internal fun Long.toLeBytes(): ByteArray = byteArrayOf(
    toByte(),
    (this shr 8).toByte(),
    (this shr 16).toByte(),
    (this shr 24).toByte(),
    (this shr 32).toByte(),
    (this shr 40).toByte(),
    (this shr 48).toByte(),
    (this shr 56).toByte(),
)

internal fun ByteArray.writeIntLe(offset: Int, value: Int) {
    this[offset] = value.toByte()
    this[offset + 1] = (value shr 8).toByte()
    this[offset + 2] = (value shr 16).toByte()
    this[offset + 3] = (value shr 24).toByte()
}

internal fun ByteArray.writeLongLe(offset: Int, value: Long) {
    this[offset] = value.toByte()
    this[offset + 1] = (value shr 8).toByte()
    this[offset + 2] = (value shr 16).toByte()
    this[offset + 3] = (value shr 24).toByte()
    this[offset + 4] = (value shr 32).toByte()
    this[offset + 5] = (value shr 40).toByte()
    this[offset + 6] = (value shr 48).toByte()
    this[offset + 7] = (value shr 56).toByte()
}

internal fun ByteArray.readIntLe(offset: Int = 0): Int =
    (this[offset].toInt() and 0xff) or
        ((this[offset + 1].toInt() and 0xff) shl 8) or
        ((this[offset + 2].toInt() and 0xff) shl 16) or
        ((this[offset + 3].toInt() and 0xff) shl 24)

internal fun ByteArray.readLongLe(offset: Int = 0): Long =
    (this[offset].toLong() and 0xff) or
        ((this[offset + 1].toLong() and 0xff) shl 8) or
        ((this[offset + 2].toLong() and 0xff) shl 16) or
        ((this[offset + 3].toLong() and 0xff) shl 24) or
        ((this[offset + 4].toLong() and 0xff) shl 32) or
        ((this[offset + 5].toLong() and 0xff) shl 40) or
        ((this[offset + 6].toLong() and 0xff) shl 48) or
        ((this[offset + 7].toLong() and 0xff) shl 56)

internal fun ByteArray.padLeft(length: Int): ByteArray {
    if (size >= length) return copyOfRange(size - length, size)
    val out = ByteArray(length)
    copyInto(out, length - size)
    return out
}

internal fun ByteArray.stripLeadingZeros(): ByteArray {
    var i = 0
    while (i < size - 1 && this[i] == 0.toByte()) i++
    return copyOfRange(i, size)
}
