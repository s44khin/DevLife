package ru.s44khin.devlife.data.network

import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object RequestManager {

    private const val MAIN_SERVER = "http://developerslife.ru/"

    private val retrofitClient: Retrofit.Builder by lazy {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        Retrofit.Builder()
            .baseUrl(MAIN_SERVER)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(MoshiConverterFactory.create())
    }

    val service: DevLifeService by lazy {
        retrofitClient
            .build()
            .create(DevLifeService::class.java)
    }
}