package com.jovinz.mvp_rx.di.components

import com.jovinz.mvp_rx.app.MoviesApplication
import com.jovinz.mvp_rx.di.modules.ActivityBuilderModule
import com.jovinz.mvp_rx.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class, // for injecting application class
        ActivityBuilderModule::class, // provides activities, as sub components behind the scenes
        AppModule::class// provides objects which will be available for scope of entire app
    ]
)
interface AppComponent : AndroidInjector<MoviesApplication?> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MoviesApplication?): Builder?
        fun build(): AppComponent?
    }
}