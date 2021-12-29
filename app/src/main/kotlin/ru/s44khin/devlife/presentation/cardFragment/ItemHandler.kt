package ru.s44khin.devlife.presentation.cardFragment

import ru.s44khin.devlife.data.model.Post

interface ItemHandler {

    fun itemOnClick(post: Post)
}