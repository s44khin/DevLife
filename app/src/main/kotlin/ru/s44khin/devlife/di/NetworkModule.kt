package ru.s44khin.devlife.di

import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.s44khin.devlife.data.network.DevLifeRepository
import ru.s44khin.devlife.data.network.DevLifeRepositoryImpl
import ru.s44khin.devlife.data.network.DevLifeService

@Module
object NetworkModule {

    private const val MAIN_SERVER = "http://developerslife.ru/"

    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    @Provides
    fun provideCallAdapterFactory() = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

    @Provides
    fun provideMoshiConverterFactory() = MoshiConverterFactory.create()

    @Provides
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
        callAdapterFactory: RxJava2CallAdapterFactory,
        moshiConverterFactory: MoshiConverterFactory
    ) = Retrofit.Builder().apply {
        baseUrl(MAIN_SERVER)
        client(okHttpClient)
        addCallAdapterFactory(callAdapterFactory)
        addConverterFactory(moshiConverterFactory)
    }.build()

    @Provides
    fun provideService(retrofit: Retrofit) = retrofit.create(DevLifeService::class.java)

    @Provides
    fun provide(service: DevLifeService): DevLifeRepository = DevLifeRepositoryImpl(service)
}