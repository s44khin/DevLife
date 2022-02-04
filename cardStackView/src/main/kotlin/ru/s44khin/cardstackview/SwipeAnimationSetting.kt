package ru.s44khin.cardstackview

import android.view.animation.AccelerateInterpolator
import android.view.animation.Interpolator
import ru.s44khin.cardstackview.internal.AnimationSetting

data class SwipeAnimationSetting(
    override var direction: Direction = Direction.Right,
    override var duration: Int = Duration.Normal.duration,
    override var interpolator: Interpolator = AccelerateInterpolator()
) : AnimationSetting