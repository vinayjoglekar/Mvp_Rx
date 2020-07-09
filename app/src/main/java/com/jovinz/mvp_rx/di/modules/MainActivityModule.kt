package com.jovinz.mvp_rx.di.modules

import com.jovinz.mvp_rx.presenter.MovieListPresenter
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun providePresenter(): MovieListPresenter = MovieListPresenter()
}
