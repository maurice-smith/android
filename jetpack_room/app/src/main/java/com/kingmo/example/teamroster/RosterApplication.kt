package com.kingmo.example.teamroster

import android.app.Application
import com.kingmo.example.teamroster.database.RosterAppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RosterApplication: Application() {
    private lateinit var appDatabase: RosterAppDatabase

    override fun onCreate() {
        super.onCreate()
        appDatabase = RosterAppDatabase.getInstance(applicationContext)
    }

    fun getAppDataBase(): RosterAppDatabase {
        return appDatabase
    }
}