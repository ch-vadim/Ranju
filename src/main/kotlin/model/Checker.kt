package model

enum class Checker {
    WHITE, BLACK;

    fun opposite(): Checker {
        return if (this == WHITE) BLACK else WHITE
    }
}