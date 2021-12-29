package ru.s44khin.devlife.data.model

import com.squareup.moshi.Json

data class BasePosts(

    @field:Json(name = "result")
    val posts: List<Post>,

    @field:Json(name = "totalCount")
    val totalCount: Int
)