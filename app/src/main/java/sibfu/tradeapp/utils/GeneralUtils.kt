package sibfu.tradeapp.utils

fun throwIllegalPositionException(position: Int): Nothing {
    throw IllegalArgumentException("Forbidden position: $position")
}
