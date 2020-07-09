package com.jovinz.mvp_rx.testutil

import com.jovinz.mvp_rx.utils.SchedulerProvider
import io.reactivex.schedulers.TestScheduler

class TestSchedulerProvider : SchedulerProvider {

    val testScheduler: TestScheduler = TestScheduler()

    override fun uiScheduler() = testScheduler
    override fun ioScheduler() = testScheduler
}