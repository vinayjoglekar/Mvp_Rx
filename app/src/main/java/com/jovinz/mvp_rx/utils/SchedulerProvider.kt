package com.jovinz.mvp_rx.utils

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun uiScheduler(): Scheduler
    fun ioScheduler(): Scheduler
}