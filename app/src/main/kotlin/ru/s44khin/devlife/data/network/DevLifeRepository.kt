package ru.s44khin.devlife.data.network

import io.reactivex.Single
import ru.s44khin.devlife.data.model.BaseComments
import ru.s44khin.devlife.data.model.BasePosts

interface DevLifeRepository {

    fun getLatest(pageNumber: Int): Single<BasePosts>

    fun getTop(pageNumber: Int): Single<BasePosts>

    fun getComments(postId: Int): Single<BaseComments>
}