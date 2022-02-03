package ru.s44khin.devlife.di.main

import dagger.BindsInstance
import dagger.Component
import ru.s44khin.devlife.data.database.DevLifeDatabase
import ru.s44khin.devlife.data.network.DevLifeRepository
import ru.s44khin.devlife.presentation.card.elm.LatestActor
import ru.s44khin.devlife.presentation.card.elm.TopActor
import ru.s44khin.devlife.presentation.favorites.elm.FavoritesActor
import ru.s44khin.devlife.presentation.post.elm.PostActor

@Component(modules = [MainModule::class])
interface MainComponent {

    val topActor: TopActor

    val latestActor: LatestActor

    val favoritesActor: FavoritesActor

    val postActor: PostActor

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun repository(repository: DevLifeRepository): Builder

        @BindsInstance
        fun database(database: DevLifeDatabase): Builder

        fun build(): MainComponent
    }
}