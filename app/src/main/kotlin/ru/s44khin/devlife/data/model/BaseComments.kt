package ru.s44khin.devlife.data.model

import com.squareup.moshi.Json

data class BaseComments(

    @field:Json(name = "comments")
    val comments: List<Comment>
)