package ru.s44khin.devlife.data.database

import ru.s44khin.devlife.data.model.Post

class DevLifeDatabaseImpl(
    private val devLifeRoomDatabase: DevLifeRoomDatabase
) : DevLifeDatabase {

    override fun getPosts() = devLifeRoomDatabase.postDao().getAll()

    override fun insert(post: Post) = devLifeRoomDatabase.postDao().insert(post)

    override fun deletePost(id: Int) = devLifeRoomDatabase.postDao().delete(id)
}