package ru.s44khin.cardstackview

enum class SwipeableMethod {
    AutomaticAndManual,
    Automatic,
    Manual,
    None;

    internal val canSwipe: Boolean
        get() = canSwipeAutomatically || canSwipeManually

    internal val canSwipeAutomatically: Boolean
        get() = this == AutomaticAndManual || this == Automatic

    internal val canSwipeManually: Boolean
        get() = this == AutomaticAndManual || this == Manual
}