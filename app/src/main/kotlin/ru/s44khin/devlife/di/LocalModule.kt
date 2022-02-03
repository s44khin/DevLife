package ru.s44khin.devlife.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.s44khin.devlife.data.database.DevLifeDatabase
import ru.s44khin.devlife.data.database.DevLifeDatabaseImpl
import ru.s44khin.devlife.data.database.DevLifeRoomDatabase

@Module
object LocalModule {

    @Provides
    fun provideRoomDataBase(context: Context) = Room.databaseBuilder(
        context,
        DevLifeRoomDatabase::class.java,
        "Database"
    ).build()

    @Provides
    fun provideDatabase(
        roomDatabase: DevLifeRoomDatabase
    ): DevLifeDatabase = DevLifeDatabaseImpl(roomDatabase)
}