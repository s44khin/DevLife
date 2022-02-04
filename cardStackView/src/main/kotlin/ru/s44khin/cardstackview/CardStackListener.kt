package ru.s44khin.cardstackview

import android.view.View

interface CardStackListener {

    companion object {
        val DEFAULT = object : CardStackListener {}
    }

    fun onCardDragging(direction: Direction, ratio: Float) {}

    fun onCardSwiped(direction: Direction) {}

    fun onCardRewound() {}

    fun onCardCanceled() {}

    fun onCardAppeared(view: View?, position: Int) {}

    fun onCardDisappeared(view: View?, position: Int) {}
}