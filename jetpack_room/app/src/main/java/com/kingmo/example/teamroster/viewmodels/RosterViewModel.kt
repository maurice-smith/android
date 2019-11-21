package com.kingmo.example.teamroster.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kingmo.example.teamroster.database.Player
import com.kingmo.example.teamroster.database.PlayerDao
import com.kingmo.example.teamroster.models.BaseCompletableObserver
import com.kingmo.example.teamroster.models.BaseObserver
import com.kingmo.example.teamroster.utils.DEFAULT_ERROR_MSG
import com.kingmo.example.teamroster.view.AddPlayerInfoClickListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RosterViewModel(private val playerDao: PlayerDao) : ViewModel() {
    val noPlayersFoundVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)
    val playerRosterVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)
    val errorViewModel: MutableLiveData<ErrorViewModel> = MutableLiveData(ErrorViewModel())
    val playersLiveData: MutableLiveData<List<PlayerViewModel>> = MutableLiveData()


    fun loadPlayers() {
        playerDao.getPlayers().subscribe(object : BaseObserver<List<Player>>() {
            override fun onNext(result: List<Player>) {
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
                val loadErrorViewModel = ErrorViewModel()
                loadErrorViewModel.message = if (!error.message?.trim().isNullOrEmpty()) error.message!! else DEFAULT_ERROR_MSG
                errorViewModel.postValue(loadErrorViewModel)
            }
        })
    }

    fun addPlayer(playerInfoForm: PlayerInfoFormViewModel, playerInfoClickListener: AddPlayerInfoClickListener) {
        playerDao.insert(convertPlayerFormToPlayerObject(playerInfoForm))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object: BaseCompletableObserver() {
            override fun onComplete() {
                playerInfoClickListener.onPlayerAddedSuccess()
            }

            override fun onError(error: Throwable) {
                val addErrorViewModel = ErrorViewModel()
                addErrorViewModel.message = if (!error.message?.trim().isNullOrEmpty()) error.message!! else DEFAULT_ERROR_MSG
                errorViewModel.postValue(addErrorViewModel)
            }
        })
    }

    fun removePlayer(playerToRemove: PlayerViewModel) {
        playerDao.delete(convertPlayerViewModelToPlayerObject(playerToRemove))
    }

    private fun convertPlayerViewModelToPlayerObject(playerViewModel: PlayerViewModel): Player = Player(
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
        bio = playerInfoForm.playerBio
    )
}

