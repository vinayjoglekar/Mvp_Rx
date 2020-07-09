package com.jovinz.mvp_rx.di.modules

import com.jovinz.mvp_rx.model.MovieListModel
import com.jovinz.mvp_rx.network.ApiInterface
import com.jovinz.mvp_rx.presenter.MovieListPresenter
import com.jovinz.mvp_rx.utils.ApiKey
import com.jovinz.mvp_rx.utils.AppSchedulerProvider
import com.jovinz.mvp_rx.utils.FilterData
import com.jovinz.mvp_rx.utils.SchedulerProvider
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainActivityModule {

    @Provides
    fun provideLanguage(): FilterData = FilterData("en")

    @Provides
    fun provideApiKey(): ApiKey = ApiKey("0ba1ead43a849d61114e60ff7322b791")

    @Provides
    fun provideAppSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

    @Provides
    fun provideApi(retrofit: Retrofit): ApiInterface = retrofit.create(ApiInterface::class.java)

    @Provides
    fun provideMovieListModel(apiInterface: ApiInterface): MovieListModel =
        MovieListModel(apiInterface)

    @Provides
    fun providePresenter(
        filterData: FilterData,
        apiKey: ApiKey,
        schedulerProvider: AppSchedulerProvider,
        movieListModel: MovieListModel
    ): MovieListPresenter =
        MovieListPresenter(filterData, apiKey, schedulerProvider, movieListModel)

}
