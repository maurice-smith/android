package com.kingmo.example.teamroster.viewmodels

import android.view.View
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.kingmo.example.teamroster.database.PlayerModel
import com.kingmo.example.teamroster.models.Response
import com.kingmo.example.teamroster.repository.PlayerRepo
import com.kingmo.example.teamroster.utils.DEFAULT_ERROR_MSG
import com.kingmo.example.teamroster.utils.getViewModelScope
import com.kingmo.example.teamroster.view.AddPlayerListener
import com.kingmo.example.teamroster.view.RosterListener
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion

class RosterViewModel @ViewModelInject constructor(private val coroutineScope: CoroutineScope? = null,
                                                   private val playerRepo: PlayerRepo,
                                                   @Assisted private val savedStateHandle: SavedStateHandle) : ViewModel() {
    val noPlayersFoundVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)
    val playerRosterVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)
    val errorViewModel: MutableLiveData<ErrorViewModel> = MutableLiveData(ErrorViewModel())
    val playersLiveData: MutableLiveData<List<PlayerViewModel>> = MutableLiveData()
    val progressBarVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)
    val playerDetails: MutableLiveData<PlayerViewModel> = MutableLiveData()
    private val viewModelCoroutineScope = getViewModelScope(coroutineScope)

    fun loadPlayers() {
        viewModelCoroutineScope.launch {
            val playersFlow = playerRepo.getPlayersFlow()
            playersFlow.collect { response ->
                when(response.status) {
                    Response.Status.LOADING -> progressBarVisibility.postValue(View.VISIBLE)
                    Response.Status.SUCCESS -> {
                        progressBarVisibility.postValue(View.GONE)
                        playersLiveData.postValue(response.data?.map { PlayerViewModel(it) })
                        playerRosterVisibility.postValue(View.VISIBLE)
                        noPlayersFoundVisibility.postValue(View.GONE)
                    }
                    Response.Status.ERROR -> {
                        playersLiveData.postValue(emptyList())
                        playerRosterVisibility.postValue(View.GONE)
                        noPlayersFoundVisibility.postValue(View.VISIBLE)
                    }
                }
            }
        }
    }

    fun addPlayer(playerInfoForm: PlayerInfoFormViewModel, addPlayerListener: AddPlayerListener) {
        viewModelCoroutineScope.launch {
            val insertFlow = playerRepo.insertPlayer(convertPlayerFormToPlayerObject(playerInfoForm))
            insertFlow.collect {
                when (it.status) {
                    Response.Status.SUCCESS -> addPlayerListener.onPlayerAddedSuccess()
                    else -> addPlayerListener.onPlayerAddError()
                }
            }
        }
    }

    fun removePlayer(playerToRemove: PlayerViewModel) {
        viewModelCoroutineScope.launch {
            val removeFlow = playerRepo.deletePlayer(convertPlayerViewModelToPlayerModel(playerToRemove))
            removeFlow.onCompletion { loadPlayers() }.collect()
        }
    }

    fun loadPlayerDetails(playerId: Int) {
        viewModelCoroutineScope.launch {
            val playerDetailFlow = playerRepo.getPlayerDetailsFlow(playerId)
            playerDetailFlow.collect {
                when (it.status) {
                    Response.Status.SUCCESS -> {
                        progressBarVisibility.postValue(View.GONE)
                        playerRosterVisibility.postValue(View.VISIBLE)
                        val playerViewModelResult = PlayerViewModel(it.data!!)
                        playerDetails.postValue(playerViewModelResult)
                    }
                    Response.Status.LOADING -> progressBarVisibility.postValue(View.VISIBLE)
                    Response.Status.ERROR -> {
                        progressBarVisibility.postValue(View.GONE)
                        errorViewModel.postValue(getErrorViewModel(Throwable("No Player Found.")))
                    }
                }
            }
        }
    }

    private fun convertPlayerViewModelToPlayerModel(playerViewModel: PlayerViewModel): PlayerModel = PlayerModel(
        playerId =  playerViewModel.getPlayerId(),
        firstName = playerViewModel.getFirstName(),
        lastName = playerViewModel.getLastName(),
        jerseyNumber = playerViewModel.getJerseyNumber(),
        position = playerViewModel.getRosterPosition(),
        bio = playerViewModel.getPlayerBio()
    )

    private fun convertPlayerFormToPlayerObject(playerInfoForm: PlayerInfoFormViewModel): PlayerModel = PlayerModel(
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

