package com.jovinz.mvp_rx.di.modules

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.jovinz.mvp_rx.utils.AppSchedulerProvider
import com.jovinz.mvp_rx.utils.SchedulerProvider
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(logger).build()
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

}