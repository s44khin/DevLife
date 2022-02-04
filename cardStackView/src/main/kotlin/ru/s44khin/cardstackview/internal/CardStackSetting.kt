package ru.s44khin.cardstackview.internal

import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import ru.s44khin.cardstackview.*

data class CardStackSetting(

    var stackFrom: StackFrom = StackFrom.None,
    var visibleCount: Int = 3,
    var translationInterval: Float = 8.0f,
    var scaleInterval: Float = 0.95f,
    var swipeThreshold: Float = 0.3f,
    var maxDegree: Float = 20.0f,
    var directions: List<Direction> = Direction.HORIZONTAL,
    var canScrollHorizontal: Boolean = true,
    var canScrollVertical: Boolean = true,
    var swipeableMethod: SwipeableMethod = SwipeableMethod.AutomaticAndManual,
    var swipeAnimationSetting: SwipeAnimationSetting = SwipeAnimationSetting(),
    var rewindAnimationSetting: RewindAnimationSetting = RewindAnimationSetting(),
    var overlayInterpolator: Interpolator = LinearInterpolator()
)