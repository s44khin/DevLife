package ru.s44khin.devlife.di.main

import dagger.Module
import dagger.Provides
import ru.s44khin.devlife.data.database.DevLifeDatabase
import ru.s44khin.devlife.data.network.DevLifeRepository
import ru.s44khin.devlife.presentation.card.elm.LatestActor

@Module
object LatestModule {

    @Provides
    fun provideLatestActor(
        repository: DevLifeRepository,
        database: DevLifeDatabase
    ) = LatestActor(repository, database)
}