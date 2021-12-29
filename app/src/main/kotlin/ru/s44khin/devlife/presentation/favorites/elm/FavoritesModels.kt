package ru.s44khin.devlife.presentation.favorites.elm

import ru.s44khin.devlife.data.model.Post

data class State(
    val posts: List<Post>? = null,
    val isLoading: Boolean = false
)

sealed class Event {

    sealed class Ui: Event() {
        object LoadPosts : Ui()
    }

    sealed class Internal: Event() {

        data class PostsLoaded(val posts: List<Post>) : Internal()

        data class ErrorLoading(val error: Throwable) : Internal()
    }
}

sealed class Effect {

    object ErrorLoading : Effect()
}

sealed class Command {

    object LoadPosts: Command()
}