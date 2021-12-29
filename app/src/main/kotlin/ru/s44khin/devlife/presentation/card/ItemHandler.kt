package ru.s44khin.devlife.presentation.card

import ru.s44khin.devlife.data.model.Post

interface ItemHandler {

    fun itemOnClick(post: Post)
}