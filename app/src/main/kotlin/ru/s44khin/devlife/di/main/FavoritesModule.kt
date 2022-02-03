package ru.s44khin.devlife.di.main

import dagger.Module
import dagger.Provides
import ru.s44khin.devlife.data.database.DevLifeDatabase
import ru.s44khin.devlife.presentation.favorites.elm.FavoritesActor

@Module
object FavoritesModule {

    @Provides
    fun provideFavoritesActor(database: DevLifeDatabase) = FavoritesActor(database)
}