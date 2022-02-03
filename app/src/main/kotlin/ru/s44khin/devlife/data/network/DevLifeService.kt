package ru.s44khin.devlife.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import ru.s44khin.devlife.data.model.BaseComments
import ru.s44khin.devlife.data.model.BasePosts

interface DevLifeService {

    companion object {
        const val PAGE_SIZE = 50
        const val LATEST = "latest"
        const val TOP = "top"
    }

    @GET("/$LATEST/{pageNumber}?json=true&pageSize=$PAGE_SIZE")
    fun getLatest(
        @Path("pageNumber") pageNumber: Int
    ): Single<BasePosts>

    @GET("/$TOP/{pageNumber}?json=true&pageSize=$PAGE_SIZE")
    fun getTop(
        @Path("pageNumber") pageNumber: Int
    ): Single<BasePosts>

    @GET("/comments/entry/{postId}")
    fun getComments(
        @Path("postId") postId: Int
    ): Single<BaseComments>
}