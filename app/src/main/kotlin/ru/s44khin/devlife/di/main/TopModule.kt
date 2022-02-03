package ru.s44khin.devlife.di.main

import dagger.Module
import dagger.Provides
import ru.s44khin.devlife.data.database.DevLifeDatabase
import ru.s44khin.devlife.data.network.DevLifeRepository
import ru.s44khin.devlife.presentation.card.elm.TopActor

@Module
object TopModule {

    @Provides
    fun provideTopActor(
        repository: DevLifeRepository,
        database: DevLifeDatabase
    ) = TopActor(repository, database)
}