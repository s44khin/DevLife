package ru.s44khin.devlife.data.network

class DevLifeRepositoryImpl(
    private val service: DevLifeService
) : DevLifeRepository {

    override fun getLatest(pageNumber: Int) = service.getLatest(pageNumber)

    override fun getTop(pageNumber: Int) = service.getTop(pageNumber)

    override fun getComments(postId: Int) = service.getComments(postId)
}