package ru.s44khin.devlife.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import ru.s44khin.devlife.data.model.Post

@Dao
interface PostDao {

    @Query("SELECT * FROM post")
    fun getAll(): Single<List<Post>>

    @Query("DELETE FROM post WHERE id = :id")
    fun delete(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: Post)
}