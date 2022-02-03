package ru.s44khin.devlife.utils.elmDialogFragment

import android.content.Context
import androidx.fragment.app.Fragment
import ru.s44khin.devlife.App
import ru.s44khin.devlife.di.app.AppComponent
import ru.s44khin.devlife.di.main.MainComponent
import ru.s44khin.devlife.presentation.MainActivity

internal val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent
    }

internal val Fragment.mainComponent: MainComponent
    get() = (activity as MainActivity).mainComponent