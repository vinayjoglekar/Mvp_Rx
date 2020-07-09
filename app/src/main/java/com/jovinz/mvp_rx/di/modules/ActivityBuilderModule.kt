package com.jovinz.mvp_rx.di.modules

import com.jovinz.mvp_rx.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(
        modules = [MainActivityModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity

}