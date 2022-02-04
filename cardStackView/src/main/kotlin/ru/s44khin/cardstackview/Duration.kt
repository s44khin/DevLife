package ru.s44khin.cardstackview

enum class Duration(val duration: Int) {

    Fast(100),
    Normal(200),
    Slow(500);

    companion object {

        fun fromVelocity(velocity: Int) = when {
            velocity < 1000 -> Slow
            velocity < 5000 -> Normal
            else -> Fast
        }
    }
}