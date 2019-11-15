package com.kingmo.example.teamroster.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kingmo.example.teamroster.database.Player
import com.kingmo.example.teamroster.database.PlayerDao
import com.kingmo.example.teamroster.models.BaseFlowableSubscriber
import com.kingmo.example.teamroster.utils.DEFAULT_ERROR_MSG

class RosterViewModel(private val playerDao: PlayerDao): ViewModel() {
    val errorMessage: MutableLiveData<ErrorViewModel> = MutableLiveData()

    fun getPlayers(): LiveData<List<PlayerViewModel>> {
        val viewModelsLiveData: MutableLiveData<List<PlayerViewModel>> = MutableLiveData()
        playerDao.getPlayers().subscribe(object : BaseFlowableSubscriber<List<Player>>() {
            override fun onNext(result: List<Player>) {
                viewModelsLiveData.postValue(result.map { PlayerViewModel(it) })
            }

            override fun onError(error: Throwable?) {
                errorMessage.postValue(ErrorViewModel(if (!error?.message?.trim().isNullOrEmpty()) error?.message!! else DEFAULT_ERROR_MSG))
            }
        })
        return viewModelsLiveData
    }

    fun addPlayer(playerToAdd: Player) {
        playerDao.insert(playerToAdd)
    }

    fun removePlayer(playerToRemove: Player) {
        playerDao.delete(playerToRemove)
    }

    fun getError(): MutableLiveData<ErrorViewModel> = errorMessage
}

