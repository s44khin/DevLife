package ru.s44khin.cardstackview.internal

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import ru.s44khin.cardstackview.CardStackLayoutManager
import ru.s44khin.cardstackview.Duration
import ru.s44khin.cardstackview.SwipeAnimationSetting
import kotlin.math.abs

class CardStackSnapHelper : SnapHelper() {

    private var velocityX = 0
    private var velocityY = 0

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray {
        if (layoutManager is CardStackLayoutManager) {
            if (layoutManager.findViewByPosition(layoutManager.topPosition) != null) {
                val x = targetView.translationX.toInt()
                val y = targetView.translationY.toInt()

                if (x != 0 || y != 0) {
                    val setting = layoutManager.setting
                    val horizontal = abs(x) / targetView.width.toFloat()
                    val vertical = abs(y) / targetView.height.toFloat()
                    val duration =
                        Duration.fromVelocity(if (velocityY < velocityX) velocityX else velocityY)

                    if (duration == Duration.Fast || setting.swipeThreshold < horizontal || setting.swipeThreshold < vertical) {
                        val state = layoutManager.state

                        if (setting.directions.contains(state.direction)) {
                            state.targetPosition = state.topPosition + 1

                            val swipeAnimationSetting = SwipeAnimationSetting(
                                direction = setting.swipeAnimationSetting.direction,
                                duration = duration.duration,
                                interpolator = setting.swipeAnimationSetting.interpolator
                            )

                            layoutManager.swipeAnimationSetting = swipeAnimationSetting

                            velocityX = 0
                            velocityY = 0

                            val scroller = CardStackSmoothScroller(
                                scrollType = CardStackSmoothScroller.ScrollType.ManualSwipe,
                                cardStackLayoutManager = layoutManager
                            )
                            scroller.targetPosition = layoutManager.topPosition
                            layoutManager.startSmoothScroll(scroller)
                        } else {
                            val scroller = CardStackSmoothScroller(
                                scrollType = CardStackSmoothScroller.ScrollType.ManualCancel,
                                cardStackLayoutManager = layoutManager
                            )
                            scroller.targetPosition = layoutManager.topPosition

                            layoutManager.startSmoothScroll(scroller)
                        }
                    } else {
                        val scroller = CardStackSmoothScroller(
                            scrollType = CardStackSmoothScroller.ScrollType.ManualCancel,
                            cardStackLayoutManager = layoutManager
                        )
                        scroller.targetPosition = layoutManager.topPosition
                        layoutManager.startSmoothScroll(scroller)
                    }
                }
            }
        }

        return IntArray(2)
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? = when {
        layoutManager is CardStackLayoutManager -> {
            layoutManager.topView?.let { view ->
                if (view.translationX.toInt() == 0 && view.translationY.toInt() == 0)
                    null
                else
                    view
            }
        }
        else -> null
    }

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager?,
        velocityX: Int,
        velocityY: Int
    ): Int {
        this.velocityX = abs(velocityX)
        this.velocityY = abs(velocityY)

        return if (layoutManager is CardStackLayoutManager)
            layoutManager.topPosition
        else
            RecyclerView.NO_POSITION
    }
}