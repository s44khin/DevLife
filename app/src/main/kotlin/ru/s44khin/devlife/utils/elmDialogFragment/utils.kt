package ru.s44khin.devlife.utils.elmDialogFragment

import android.content.Context
import ru.s44khin.devlife.App
import ru.s44khin.devlife.di.AppComponent

internal val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent
    }