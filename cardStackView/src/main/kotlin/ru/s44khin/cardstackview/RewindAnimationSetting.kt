package ru.s44khin.cardstackview

import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import ru.s44khin.cardstackview.internal.AnimationSetting

data class RewindAnimationSetting(
    override var direction: Direction = Direction.Bottom,
    override var duration: Int = Duration.Normal.duration,
    override var interpolator: Interpolator = DecelerateInterpolator()
) : AnimationSetting