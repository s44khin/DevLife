package ru.s44khin.devlife.presentation.favorites.elm

import io.reactivex.Observable
import ru.s44khin.devlife.data.database.DevLifeDatabase
import vivid.money.elmslie.core.ActorCompat

class FavoritesActor(
    private val database: DevLifeDatabase
) : ActorCompat<Command, Event> {

    override fun execute(command: Command): Observable<Event> = when (command) {

        is Command.LoadPosts -> database.postDao().getAll()
            .mapEvents(
                { posts -> Event.Internal.PostsLoaded(posts) },
                { error -> Event.Internal.ErrorLoading(error) }
            )
    }
}