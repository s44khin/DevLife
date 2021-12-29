package ru.s44khin.devlife.presentation.favorites.elm

import ru.s44khin.devlife.data.model.Post

data class State(
    val posts: List<Post>? = null,
    val isLoading: Boolean = false
)

sealed class Event {

    sealed class Ui : Event() {
        object LoadPosts : Ui()

        data class DeletePost(val id: Int) : Ui()
    }

    sealed class Internal : Event() {

        data class PostsLoaded(val posts: List<Post>) : Internal()

        object PostDeleted : Internal()

        data class ErrorLoading(val error: Throwable) : Internal()

        data class ErrorDeletePost(val error: Throwable) : Internal()
    }
}

sealed class Effect {

    object ErrorLoading : Effect()

    object ErrorDeletePost : Effect()
}

sealed class Command {

    object LoadPosts : Command()

    data class DeletePost(val id: Int) : Command()
}