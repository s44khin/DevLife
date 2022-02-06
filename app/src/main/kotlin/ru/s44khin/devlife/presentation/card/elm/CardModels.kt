package ru.s44khin.devlife.presentation.card.elm

import ru.s44khin.devlife.data.model.Post

enum class Type {
    LATEST, TOP
}

data class State(
    val posts: List<Post> = emptyList(),
    val isLoadingNetwork: Boolean = false,
    val currentPage: Int = 0
)

sealed class Event {

    sealed class Ui : Event() {
        object LoadPosts : Ui()

        data class SaveToDatabase(val post: Post) : Ui()
    }

    sealed class Internal : Event() {

        data class PageLoadedNetwork(val posts: List<Post>) : Internal()

        data class ErrorLoadingNetwork(val error: Throwable) : Internal()
    }
}

sealed class Effect

sealed class Command {

    data class LoadPage(val page: Int) : Command()

    data class SaveToDatabase(val post: Post) : Command()
}