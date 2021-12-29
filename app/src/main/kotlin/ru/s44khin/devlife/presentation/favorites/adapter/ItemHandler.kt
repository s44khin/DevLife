package ru.s44khin.devlife.presentation.favorites.adapter

import ru.s44khin.devlife.data.model.Post

interface ItemHandler {

    fun itemOnClick(post: Post)

    fun removeOnClick(id: Int)
}