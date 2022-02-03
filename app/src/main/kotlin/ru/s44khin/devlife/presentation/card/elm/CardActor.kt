package ru.s44khin.devlife.presentation.card.elm

import io.reactivex.Observable
import ru.s44khin.devlife.data.database.DevLifeDatabase
import ru.s44khin.devlife.data.network.DevLifeRepository
import vivid.money.elmslie.core.ActorCompat

class CardActor(
    private val repository: DevLifeRepository,
    private val database: DevLifeDatabase,
    private val type: Type
) : ActorCompat<Command, Event> {

    override fun execute(command: Command): Observable<Event> = when (command) {

        is Command.LoadPage -> when (type) {

            is Type.LATEST -> repository.getLatest(command.page)
                .mapEvents(
                    { requestResult -> Event.Internal.PageLoadedNetwork(requestResult.posts) },
                    { error -> Event.Internal.ErrorLoadingNetwork(error) }
                )

            is Type.TOP -> repository.getTop(command.page)
                .mapEvents(
                    { requestResult -> Event.Internal.PageLoadedNetwork(requestResult.posts) },
                    { error -> Event.Internal.ErrorLoadingNetwork(error) }
                )
        }

        is Command.SaveToDatabase -> Observable.fromCallable {
            database.insert(command.post)
        }
            .mapEvents(
                { Event.Internal.PostSaved },
                { error -> Event.Internal.ErrorSavePost(error) }
            )
    }
}