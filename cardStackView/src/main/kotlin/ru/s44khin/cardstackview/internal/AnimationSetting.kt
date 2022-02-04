package ru.s44khin.cardstackview.internal

import android.view.animation.Interpolator
import ru.s44khin.cardstackview.Direction

interface AnimationSetting {

    val direction: Direction

    val duration: Int

    val interpolator: Interpolator
}