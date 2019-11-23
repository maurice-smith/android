package com.kingmo.example.teamroster.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kingmo.example.teamroster.database.Player
import com.kingmo.example.teamroster.database.PlayerDao
import com.kingmo.example.teamroster.models.BaseCompletableObserver
import com.kingmo.example.teamroster.models.BaseObserver
import com.kingmo.example.teamroster.utils.DEFAULT_ERROR_MSG
import com.kingmo.example.teamroster.utils.schedulers.SchedulerProvider
import com.kingmo.example.teamroster.view.PlayerInfoClickListener

class RosterViewModel(private val playerDao: PlayerDao, private val scheduleProvider: SchedulerProvider) : ViewModel() {
    val noPlayersFoundVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)
    val playerRosterVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)
    val errorViewModel: MutableLiveData<ErrorViewModel> = MutableLiveData(ErrorViewModel())
    val playersLiveData: MutableLiveData<List<PlayerViewModel>> = MutableLiveData()
    val progressBarVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)

    fun loadPlayers() {
        playerDao.getPlayers()
            .doOnSubscribe {progressBarVisibility.postValue(View.VISIBLE)}
            .subscribe(object : BaseObserver<List<Player>>() {
            override fun onNext(result: List<Player>) {
                progressBarVisibility.postValue(View.GONE)
                if (result.isNotEmpty()) {
                    playersLiveData.postValue(result.map { PlayerViewModel(it) })
                    playerRosterVisibility.postValue(View.VISIBLE)
                    noPlayersFoundVisibility.postValue(View.GONE)
                } else {
                    playerRosterVisibility.postValue(View.GONE)
                    noPlayersFoundVisibility.postValue(View.VISIBLE)
                }
            }

            override fun onError(error: Throwable) {
                progressBarVisibility.postValue(View.GONE)
                errorViewModel.postValue(getErrorViewModel(error))
            }
        })
    }

    fun addPlayer(playerInfoForm: PlayerInfoFormViewModel, playerInfoClickListener: PlayerInfoClickListener) {
        playerDao.insert(convertPlayerFormToPlayerObject(playerInfoForm))
            .observeOn(scheduleProvider.mainThread())
            .subscribeOn(scheduleProvider.backgroundThread())
            .doOnSubscribe {progressBarVisibility.postValue(View.VISIBLE)}
            .subscribe(object: BaseCompletableObserver() {
            override fun onComplete() {
                playerInfoClickListener.onPlayerAddedSuccess()
            }

            override fun onError(error: Throwable) {
                progressBarVisibility.postValue(View.GONE)
                errorViewModel.postValue(getErrorViewModel(error))
            }
        })
    }

    fun removePlayer(playerToRemove: PlayerViewModel) {
        playerDao.delete(convertPlayerViewModelToPlayerObject(playerToRemove))
            .observeOn(scheduleProvider.mainThread())
            .subscribeOn(scheduleProvider.backgroundThread())
            .doOnSubscribe {progressBarVisibility.postValue(View.VISIBLE)}
            .subscribe(object : BaseCompletableObserver() {

                override fun onError(error: Throwable) {
                    progressBarVisibility.postValue(View.GONE)
                    errorViewModel.postValue(getErrorViewModel(error))
                }
            })
    }

    private fun convertPlayerViewModelToPlayerObject(playerViewModel: PlayerViewModel): Player = Player(
        playerId =  playerViewModel.getPlayerId(),
        firstName = playerViewModel.getFirstName(),
        lastName = playerViewModel.getLastName(),
        jerseyNumber = playerViewModel.getJerseyNumber(),
        position = playerViewModel.getRosterPosition(),
        bio = playerViewModel.getPlayerBio()
    )

    private fun convertPlayerFormToPlayerObject(playerInfoForm: PlayerInfoFormViewModel): Player = Player(
        firstName = playerInfoForm.firstName,
        lastName = playerInfoForm.lastName,
        jerseyNumber = playerInfoForm.jerseyNumber,
        position = playerInfoForm.position,
        bio = playerInfoForm.playerBio,
        photoUrl = playerInfoForm.profileUrl
    )

    private fun getErrorViewModel(error: Throwable): ErrorViewModel {
        val loadErrorViewModel = ErrorViewModel()
        loadErrorViewModel.message = if (!error.message?.trim().isNullOrEmpty()) error.message!! else DEFAULT_ERROR_MSG
        return loadErrorViewModel
    }
}

