package ru.s44khin.devlife.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.s44khin.devlife.data.database.dao.PostDao
import ru.s44khin.devlife.data.model.Post

@Database(entities = [Post::class], version = 1)
abstract class DevLifeDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao
}