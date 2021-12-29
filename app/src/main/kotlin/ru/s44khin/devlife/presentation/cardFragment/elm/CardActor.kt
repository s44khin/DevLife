package ru.s44khin.devlife.presentation.cardFragment.elm

import io.reactivex.Observable
import ru.s44khin.devlife.data.network.DevLifeRepository
import vivid.money.elmslie.core.ActorCompat

class CardActor(
    private val repository: DevLifeRepository,
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
    }
}