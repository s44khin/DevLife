package ru.s44khin.devlife.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "post")
data class Post(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:Json(name = "id")
    val id: Int,

    @ColumnInfo(name = "description")
    @field:Json(name = "description")
    val description: String,

    @ColumnInfo(name = "votes")
    @field:Json(name = "votes")
    val votes: Int,

    @ColumnInfo(name = "author")
    @field:Json(name = "author")
    val author: String,

    @ColumnInfo(name = "date")
    @field:Json(name = "date")
    val date: String,

    @ColumnInfo(name = "gifURL")
    @field:Json(name = "gifURL")
    val gifURL: String,

    @ColumnInfo(name = "previewURL")
    @field:Json(name = "previewURL")
    val previewURL: String
) : Parcelable