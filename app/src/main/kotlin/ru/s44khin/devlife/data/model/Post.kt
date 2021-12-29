package ru.s44khin.devlife.data.model

import com.squareup.moshi.Json

data class Post(

    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "description")
    val description: String,

    @field:Json(name = "votes")
    val votes: Int,

    @field:Json(name = "author")
    val author: String,

    @field:Json(name = "date")
    val date: String,

    @field:Json(name = "gifURL")
    val gifURL: String,

    @field:Json(name = "previewURL")
    val previewURL: String
)