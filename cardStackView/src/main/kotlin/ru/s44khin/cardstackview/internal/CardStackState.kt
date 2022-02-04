package ru.s44khin.cardstackview.internal

import androidx.recyclerview.widget.RecyclerView
import ru.s44khin.cardstackview.Direction
import kotlin.math.abs
import kotlin.math.min

class CardStackState {

    var status = Status.Idle
    var width = 0
    var height = 0
    var dx = 0
    var dy = 0
    var topPosition = 0
    var targetPosition = RecyclerView.NO_POSITION
    var proportion = 0.0f

    val direction: Direction
        get() = if (abs(dy) < abs(dx))
            if (dx < 0.0f)
                Direction.Left
            else
                Direction.Right
        else
            if (dy < 0.0f)
                Direction.Top
            else
                Direction.Bottom

    val ratio: Float
        get() {
            val absDx = abs(dx)
            val absDy = abs(dy)

            return min(
                if (absDx < absDy)
                    absDy / (height / 2.0f)
                else
                    absDx / (width / 2.0f),
                1.0f
            )
        }

    fun next(status: Status) {
        this.status = status
    }

    fun isSwipeCompleted() = status.isSwipeAnimating() && (width < abs(dx) || height < abs(dy))

    fun canScrollToPosition(position: Int, itemCount: Int) = when {
        position == topPosition -> false
        position < 0 -> false
        itemCount < position -> false
        status.isBusy() -> false
        else -> true
    }

    enum class Status {
        Idle,
        Dragging,
        RewindAnimating,
        AutomaticSwipeAnimating,
        AutomaticSwipeAnimated,
        ManualSwipeAnimating,
        ManualSwipeAnimated;

        fun isBusy() = this != Idle

        fun isDragging() = this == Dragging

        fun isSwipeAnimating() = this == ManualSwipeAnimating || this == AutomaticSwipeAnimating

        fun toAnimatedStatus() = when (this) {
            ManualSwipeAnimating -> ManualSwipeAnimated
            AutomaticSwipeAnimating -> AutomaticSwipeAnimated
            else -> Idle
        }
    }
}