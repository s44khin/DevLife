package ru.s44khin.devlife

import android.app.Application
import com.google.android.material.color.DynamicColors
import ru.s44khin.devlife.data.network.DevLifeRepository
import ru.s44khin.devlife.data.network.RequestManager

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    lateinit var repository: DevLifeRepository

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
        instance = this
        repository = DevLifeRepository(RequestManager.service)
    }
}