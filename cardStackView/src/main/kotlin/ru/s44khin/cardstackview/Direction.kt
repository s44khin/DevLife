package ru.s44khin.cardstackview

enum class Direction {
    Left,
    Right,
    Top,
    Bottom;

    companion object {
        val HORIZONTAL = arrayListOf(Left, Right)
        val VERTICAL = arrayListOf(Top, Bottom)
        val FREEDOM = arrayListOf(values())
    }
}