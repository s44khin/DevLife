package ru.s44khin.devlife.presentation.post.elm

import io.reactivex.Observable
import ru.s44khin.devlife.data.network.DevLifeRepository
import vivid.money.elmslie.core.ActorCompat

class PostActor(
    private val repository: DevLifeRepository
) : ActorCompat<Command, Event> {

    override fun execute(command: Command): Observable<Event> = when (command) {

        is Command.LoadComments -> repository.getComments(command.postId)
            .mapEvents(
                { baseComments -> Event.Internal.CommentsLoaded(baseComments.comments) },
                { error -> Event.Internal.ErrorLoading(error) }
            )
    }
}