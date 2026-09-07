package iris.kmtproto.events

fun interface EventFilter<T> {
    suspend fun test(value: T): Boolean
}

infix fun <T> EventFilter<T>.and(other: EventFilter<T>): EventFilter<T> =
    EventFilter { test(it) && other.test(it) }

infix fun <T> EventFilter<T>.or(other: EventFilter<T>): EventFilter<T> =
    EventFilter { test(it) || other.test(it) }

operator fun <T> EventFilter<T>.not(): EventFilter<T> =
    EventFilter { !test(it) }
