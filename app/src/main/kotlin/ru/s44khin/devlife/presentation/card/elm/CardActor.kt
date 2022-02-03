package ru.s44khin.devlife.presentation.card.elm

import io.reactivex.Observable
import ru.s44khin.devlife.data.database.DevLifeDatabase
import ru.s44khin.devlife.data.network.DevLifeRepository
import vivid.money.elmslie.core.ActorCompat

class LatestActor(
    val repository: DevLifeRepository,
    val database: DevLifeDatabase
) : ActorCompat<Command, Event> {

    override fun execute(command: Command): Observable<Event> = when (command) {

        is Command.LoadPage -> repository.getLatest(command.page)
            .mapEvents(
                { requestResult -> Event.Internal.PageLoadedNetwork(requestResult.posts) },
                { error -> Event.Internal.ErrorLoadingNetwork(error) }
            )

        is Command.SaveToDatabase -> Observable.fromCallable {
            database.insert(command.post)
        }
            .mapEvents(
                { Event.Internal.PostSaved },
                { error -> Event.Internal.ErrorSavePost(error) }
            )
    }
}

class TopActor(
    val repository: DevLifeRepository,
    val database: DevLifeDatabase
) : ActorCompat<Command, Event> {

    override fun execute(command: Command): Observable<Event> = when (command) {

        is Command.LoadPage -> repository.getTop(command.page)
            .mapEvents(
                { requestResult -> Event.Internal.PageLoadedNetwork(requestResult.posts) },
                { error -> Event.Internal.ErrorLoadingNetwork(error) }
            )

        is Command.SaveToDatabase -> Observable.fromCallable {
            database.insert(command.post)
        }
            .mapEvents(
                { Event.Internal.PostSaved },
                { error -> Event.Internal.ErrorSavePost(error) }
            )
    }
}