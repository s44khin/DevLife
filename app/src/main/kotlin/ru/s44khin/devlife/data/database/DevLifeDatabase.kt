package ru.s44khin.devlife.data.database

import io.reactivex.Single
import ru.s44khin.devlife.data.model.Post

interface DevLifeDatabase {

    fun getPosts(): Single<List<Post>>

    fun deletePost(id: Int)

    fun insert(post: Post)
}