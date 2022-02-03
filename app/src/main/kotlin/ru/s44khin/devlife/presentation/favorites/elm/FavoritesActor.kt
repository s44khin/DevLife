package ru.s44khin.devlife.presentation.favorites.elm

import io.reactivex.Observable
import ru.s44khin.devlife.data.database.DevLifeDatabase
import vivid.money.elmslie.core.ActorCompat

class FavoritesActor(
    val database: DevLifeDatabase
) : ActorCompat<Command, Event> {

    override fun execute(command: Command): Observable<Event> = when (command) {

        is Command.LoadPosts -> database.getPosts()
            .mapEvents(
                { posts -> Event.Internal.PostsLoaded(posts) },
                { error -> Event.Internal.ErrorLoading(error) }
            )

        is Command.DeletePost -> Observable.fromCallable {
            database.deletePost(command.id)
        }
            .mapEvents(
                { Event.Internal.PostDeleted },
                { error -> Event.Internal.ErrorDeletePost(error) }
            )
    }
}