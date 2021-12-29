package ru.s44khin.devlife.presentation.postFragment.elm

import ru.s44khin.devlife.data.model.Comment

data class State(
    val comments: List<Comment> = emptyList(),
    val isLoadingNetwork: Boolean = false,
)

sealed class Event {

    sealed class Ui: Event() {
        data class LoadComments(val postId: Int) : Ui()
    }

    sealed class Internal : Event() {

        data class CommentsLoaded(val comments: List<Comment>) : Internal()

        data class ErrorLoading(val error: Throwable) : Internal()
    }
}

sealed class Effect {
    object ErrorLoading: Effect()
}

sealed class Command {

    data class LoadComments(val postId: Int) : Command()
}