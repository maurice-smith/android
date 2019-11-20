package com.kingmo.example.teamroster.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kingmo.example.teamroster.database.Player
import com.kingmo.example.teamroster.database.PlayerDao
import com.kingmo.example.teamroster.models.BaseFlowableSubscriber
import com.kingmo.example.teamroster.utils.DEFAULT_ERROR_MSG

class RosterViewModel(private val playerDao: PlayerDao) : ViewModel() {
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

    fun addPlayer(playerToAdd: PlayerViewModel) {
        playerDao.insert(convertPlayerViewModelToPlayerObject(playerToAdd))
    }

    fun removePlayer(playerToRemove: PlayerViewModel) {
        playerDao.delete(convertPlayerViewModelToPlayerObject(playerToRemove))
    }

    fun getError(): MutableLiveData<ErrorViewModel> = errorMessage

    private fun convertPlayerViewModelToPlayerObject(playerViewModel: PlayerViewModel): Player = Player(
        firstName = playerViewModel.getFirstName(),
        lastName = playerViewModel.getLastName(),
        jerseyNumber = playerViewModel.getJerseyNumber(),
        position = playerViewModel.getRosterPosition(),
        bio = playerViewModel.getPlayerBio()
    )
}

