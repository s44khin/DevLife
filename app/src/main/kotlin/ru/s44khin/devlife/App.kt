package ru.s44khin.devlife

import android.app.Application
import com.google.android.material.color.DynamicColors
import ru.s44khin.devlife.di.AppComponent
import ru.s44khin.devlife.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}