package ru.s44khin.devlife.presentation.postFragment.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class PostReducer : DslReducer<Event, State, Effect, Command>() {

    override fun Result.reduce(event: Event) = when (event) {

        is Event.Ui.LoadComments -> {
            state { copy(isLoadingNetwork = true) }
            commands { +Command.LoadComments(event.postId) }
        }

        is Event.Internal.CommentsLoaded -> {
            state { copy(isLoadingNetwork = false, comments = event.comments) }
        }

        is Event.Internal.ErrorLoading -> {
            state { copy(isLoadingNetwork = false) }
            effects { +Effect.ErrorLoading }
        }
    }
}