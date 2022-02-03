package ru.s44khin.devlife.data.network

class DevLifeRepositoryImpl(
    private val service: DevLifeService
) : DevLifeRepository {

    override fun getLatest(pageNumber: Int) = service.getPosts(
        chapter = DevLifeService.LATEST,
        pageNumber = pageNumber
    )

    override fun getTop(pageNumber: Int) = service.getPosts(
        chapter = DevLifeService.TOP,
        pageNumber = pageNumber
    )

    override fun getComments(postId: Int) = service.getComments(postId)
}