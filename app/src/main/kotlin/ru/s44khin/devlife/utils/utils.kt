package ru.s44khin.devlife.utils

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import ru.s44khin.devlife.App
import ru.s44khin.devlife.R
import ru.s44khin.devlife.di.app.AppComponent
import ru.s44khin.devlife.di.main.MainComponent
import ru.s44khin.devlife.presentation.MainActivity

internal val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent
    }

internal val Fragment.mainComponent: MainComponent
    get() = if (activity is MainActivity)
        (activity as MainActivity).mainComponent
    else
        error("activity in not MainActivity")

internal fun getLoader(context: Context) = CircularProgressDrawable(context).apply {
    strokeWidth = context.resources.displayMetrics.density * 5f
    centerRadius = context.resources.displayMetrics.density * 20f
    setColorSchemeColors(R.color.primary)
    start()
}