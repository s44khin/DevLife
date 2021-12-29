package ru.s44khin.devlife.data.network

class DevLifeRepository(
    private val service: DevLifeService
) {

    fun getLatest(pageNumber: Int) = service.getPosts(DevLifeService.LATEST, pageNumber)

    fun getHot(pageNumber: Int) = service.getPosts(DevLifeService.HOT, pageNumber)

    fun getTop(pageNumber: Int) = service.getPosts(DevLifeService.TOP, pageNumber)

    fun getComments(postId: Int) = service.getComments(postId)
}