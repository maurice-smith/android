package com.kingmo.example.teamroster.utils.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SchedulerProvider {
    fun backgroundThread(): Scheduler
    fun mainThread(): Scheduler
}

// Allows for trampoline aka test schedulers to be used
class TestSchedulerProvider: SchedulerProvider {
    override fun backgroundThread(): Scheduler = Schedulers.trampoline()
    override fun mainThread(): Scheduler = Schedulers.trampoline()
}

class AppScheduleProvider: SchedulerProvider {
    override fun backgroundThread(): Scheduler = Schedulers.io()
    override fun mainThread(): Scheduler = AndroidSchedulers.mainThread()
}
