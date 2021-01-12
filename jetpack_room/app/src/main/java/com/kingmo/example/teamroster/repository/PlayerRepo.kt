package com.kingmo.example.teamroster.repository

import com.kingmo.example.teamroster.database.PlayerDao
import com.kingmo.example.teamroster.database.PlayerModel
import com.kingmo.example.teamroster.models.Response
import com.kingmo.example.teamroster.utils.coroutines.CoroutineContextProvider
import javax.inject.Inject
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PlayerRepo @Inject constructor(private val playerDao: PlayerDao, private val coroutineContextProvider: CoroutineContextProvider? = null) {

    suspend fun getPlayersAsync(): Deferred<Response<List<PlayerModel>>> {
        var output: CompletableDeferred<Response<List<PlayerModel>>> = CompletableDeferred(Response.loading())
        withContext(getCoroutineContext()) {
            val playerTask = async { playerDao.getPlayers() }
            val playerList = playerTask.await()

            output = if (playerList.isEmpty()) {
                CompletableDeferred(Response.error())
            } else {
                CompletableDeferred(Response.success(playerList))

            }
        }
        return output
    }

    suspend fun insertPlayer(vararg playerModel: PlayerModel) = withContext(Dispatchers.IO) {
        playerDao.insert(*playerModel)
    }

    suspend fun getPlayerDetailsAsync(playerId: Int): Deferred<Response<PlayerModel?>> {
        var playerDeferred: CompletableDeferred<Response<PlayerModel?>> = CompletableDeferred(Response.loading())
        withContext(getCoroutineContext()) {
            val playerTask = async { playerDao.findPlayerById(playerId) }
            val playerModel = playerTask.await()

            playerDeferred = if (playerModel == null) {
                CompletableDeferred(Response.error())
            } else {
                CompletableDeferred(Response.success(playerModel))
            }
        }
        return playerDeferred
    }

    suspend fun deletePlayer(user: PlayerModel) = withContext(getCoroutineContext()) {
        playerDao.delete(user)
    }

    private fun getCoroutineContext(): CoroutineContext = coroutineContextProvider?.IO ?: Dispatchers.IO
}