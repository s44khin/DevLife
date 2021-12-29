package ru.s44khin.devlife.presentation.favorites.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class FavoritesReducer : DslReducer<Event, State, Effect, Command>() {

    override fun Result.reduce(event: Event) = when (event) {

        is Event.Ui.LoadPosts -> {
            state { copy(isLoading = true) }
            commands { +Command.LoadPosts }
        }

        is Event.Ui.DeletePost -> {
            commands { +Command.DeletePost(event.id) }
        }

        is Event.Internal.PostsLoaded -> {
            state { copy(isLoading = false, posts = event.posts) }
        }

        is Event.Internal.PostDeleted -> {
            commands { +Command.LoadPosts }
        }

        is Event.Internal.ErrorLoading -> {
            state { copy(isLoading = false) }
            effects { +Effect.ErrorLoading }
        }

        is Event.Internal.ErrorDeletePost -> {
            effects { +Effect.ErrorDeletePost }
        }
    }
}