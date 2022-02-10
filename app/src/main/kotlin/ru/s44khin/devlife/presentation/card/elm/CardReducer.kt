package ru.s44khin.devlife.presentation.card.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class CardReducer : DslReducer<Event, State, Effect, Command>() {

    override fun Result.reduce(event: Event) = when (event) {

        is Event.Ui.LoadPosts -> {
            commands { +Command.LoadPage(state.currentPage) }
            state { copy(isLoadingNetwork = true, currentPage = currentPage + 1) }
        }

        is Event.Ui.SaveToDatabase -> {
            commands { +Command.SaveToDatabase(event.post) }
        }

        is Event.Internal.PageLoadedNetwork -> {
            state { copy(isLoadingNetwork = false, posts = state.posts + event.posts) }
        }

        is Event.Internal.ErrorLoadingNetwork -> {
            state { copy(isLoadingNetwork = false) }
        }
    }
}