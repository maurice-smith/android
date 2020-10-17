package com.kingmo.example.teamroster.repository

import com.kingmo.example.teamroster.database.PlayerDao
import com.kingmo.example.teamroster.database.PlayerModel
import com.kingmo.example.teamroster.utils.schedulers.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class PlayerRepo @Inject constructor(private val playerDao: PlayerDao, private val scheduleProvider: SchedulerProvider) {
    fun getPlayers(): Observable<List<PlayerModel>> = playerDao.getPlayers()
        .observeOn(scheduleProvider.mainThread())
        .subscribeOn(scheduleProvider.backgroundThread())

    fun insertPlayer(vararg playerModel: PlayerModel): Completable = playerDao.insert(*playerModel)
        .observeOn(scheduleProvider.mainThread())
        .subscribeOn(scheduleProvider.backgroundThread())

    fun getPlayerDetails(playerId: Int): Observable<PlayerModel> = playerDao.findPlayerById(playerId)
        .observeOn(scheduleProvider.mainThread())
        .subscribeOn(scheduleProvider.backgroundThread())

    fun deletePlayer(user: PlayerModel): Completable = playerDao.delete(user)
        .observeOn(scheduleProvider.mainThread())
        .subscribeOn(scheduleProvider.backgroundThread())
}