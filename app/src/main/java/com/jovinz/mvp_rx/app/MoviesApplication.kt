package com.jovinz.mvp_rx.app

import com.jovinz.mvp_rx.di.components.DaggerAppComponent
import dagger.android.support.DaggerApplication


class MoviesApplication : DaggerApplication() {
    override fun applicationInjector() = DaggerAppComponent.builder()
        .application(this)
        ?.build()
}