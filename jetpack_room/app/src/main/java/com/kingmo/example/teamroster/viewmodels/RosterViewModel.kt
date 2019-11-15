package com.kingmo.example.teamroster.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kingmo.example.teamroster.database.Player
import com.kingmo.example.teamroster.database.PlayerDao
import io.reactivex.FlowableSubscriber
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.reactivestreams.Subscription

class RosterViewModel(private val playerDao: PlayerDao): ViewModel() {
    val errorMessage: MutableLiveData<ErrorViewModel> = MutableLiveData()

    fun getPlayers(): LiveData<List<PlayerViewModel>> {
        val viewModelsLiveData: MutableLiveData<List<PlayerViewModel>> = MutableLiveData()
        playerDao.getPlayers().subscribe(object : BaseFlowableSubscriber<List<Player>>() {
            override fun onNext(result: List<Player>) {
                viewModelsLiveData.postValue(result.map { PlayerViewModel(it) })
            }

            override fun onError(error: Throwable?) {
                errorMessage.postValue(ErrorViewModel(if (error?.message == null) "" else error.message!!))
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

abstract class BaseObserver<T>: Observer<T> {
    override fun onComplete() {
        //no-op
    }

    override fun onSubscribe(d: Disposable) {
        //no-op
    }

    override fun onNext(result: T) {
        //no-op
    }

    override fun onError(error: Throwable) {
        //no-op
    }
}

abstract class BaseFlowableSubscriber<T>: FlowableSubscriber<T> {
    override fun onComplete() {
        //no-op
    }

    override fun onSubscribe(s: Subscription) {
        //no-op
    }

    override fun onNext(result: T) {
        //no-op
    }

    override fun onError(error: Throwable?) {
        //no-op
    }
}
