package ru.s44khin.devlife.di.main

import dagger.Module
import dagger.Provides
import ru.s44khin.devlife.data.network.DevLifeRepository
import ru.s44khin.devlife.presentation.post.elm.PostActor

@Module
object PostModule {

    @Provides
    fun providePostActor(repository: DevLifeRepository) = PostActor(repository)
}