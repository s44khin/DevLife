package ru.s44khin.devlife.data.model

import com.squareup.moshi.Json

data class Comment(
    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "text")
    val text: String,

    @field:Json(name = "date")
    val date: String,

    @field:Json(name = "voteCount")
    val voteCount: Int,

    @field:Json(name = "authorId")
    val authorId: Int,

    @field:Json(name = "authorName")
    val authorName: String,

    @field:Json(name = "entryId")
    val entryId: Int,

    @field:Json(name = "deleted")
    val deleted: Boolean,

    @field:Json(name = "voted")
    val voted: Boolean,

    @field:Json(name = "editable")
    val editable: Boolean
)