package ru.s44khin.devlife

import android.app.Application
import androidx.room.Room
import com.google.android.material.color.DynamicColors
import ru.s44khin.devlife.data.database.DevLifeDatabase
import ru.s44khin.devlife.data.network.DevLifeRepository
import ru.s44khin.devlife.data.network.RequestManager

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    lateinit var repository: DevLifeRepository
    lateinit var database: DevLifeDatabase

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
        instance = this
        repository = DevLifeRepository(RequestManager.service)
        database = Room.databaseBuilder(
            this,
            DevLifeDatabase::class.java,
            "Database"
        ).build()
    }
}