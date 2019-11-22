package com.kingmo.example.teamroster.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kingmo.example.teamroster.database.PlayerDao
import com.kingmo.example.teamroster.utils.schedulers.SchedulerProvider


class AppViewModelFactory(private val playerDao: PlayerDao,
                          private val scheduleProvider: SchedulerProvider): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RosterViewModel::class.java)) {
            return RosterViewModel(playerDao, scheduleProvider) as T
        } else if (modelClass.isAssignableFrom(ErrorViewModel::class.java)) {
            return ErrorViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}