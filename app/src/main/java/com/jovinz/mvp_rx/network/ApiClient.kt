package com.jovinz.mvp_rx.network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    private var retrofit: Retrofit? = null
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w200/"
    const val BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780/"

    /**
     * This method returns retrofit client instance
     *
     * @return Retrofit object
     */
    val client: Retrofit?
        get() {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(logger).build()

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }
            return retrofit
        }
}