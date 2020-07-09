package com.jovinz.mvp_rx.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Tamas_Kozmer on 8/4/2017.
 */
class AppSchedulerProvider @Inject constructor() : SchedulerProvider {
    override fun ioScheduler() = Schedulers.io()
    override fun uiScheduler(): Scheduler = AndroidSchedulers.mainThread()
}