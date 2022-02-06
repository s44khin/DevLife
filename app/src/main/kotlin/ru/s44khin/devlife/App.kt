package ru.s44khin.devlife

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.android.material.color.DynamicColors
import ru.s44khin.devlife.di.app.AppComponent
import ru.s44khin.devlife.di.app.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
        Fresco.initialize(this)
        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}