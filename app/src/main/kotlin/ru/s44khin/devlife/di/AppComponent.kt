package ru.s44khin.devlife.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.s44khin.devlife.data.database.DevLifeDatabase
import ru.s44khin.devlife.data.network.DevLifeRepository

@Component(modules = [AppModule::class])
interface AppComponent {

    val repository: DevLifeRepository

    val database: DevLifeDatabase

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}