package ru.s44khin.cardstackview.internal

import android.content.Context

fun Float.toPx(context: Context): Int {
    val density = context.resources.displayMetrics.density
    return (this * density + 0.5f).toInt()
}