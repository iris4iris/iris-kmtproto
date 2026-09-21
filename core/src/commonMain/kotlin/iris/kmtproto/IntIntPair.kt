package iris.kmtproto

class IntIntPair(val first: Int, val second: Int) {
    operator fun component1(): Int = first
    operator fun component2(): Int = second
}

class LongIntIntTriple(val first: Long, val second: Int, val third: Int) {
    operator fun component1(): Long = first
    operator fun component2(): Int = second
    operator fun component3(): Int = third
}
