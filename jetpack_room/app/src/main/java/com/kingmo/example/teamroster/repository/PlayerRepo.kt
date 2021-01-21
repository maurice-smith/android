package com.kingmo.example.teamroster.repository

import com.kingmo.example.teamroster.database.PlayerDao
import com.kingmo.example.teamroster.database.PlayerModel
import com.kingmo.example.teamroster.models.Response
import com.kingmo.example.teamroster.utils.coroutines.CoroutineContextProvider
import javax.inject.Inject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
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

    suspend fun getPlayersFlow(): Flow<Response<List<PlayerModel>>> {
        return flowOf(playerDao.getPlayers()).map {
            if (it.isEmpty()) {
                Response.error()
            } else {
                Response.success(it)
            }
        }.catch {
            emit(Response.error())
        }.flowOn(getCoroutineContext())
    }

    suspend fun insertPlayer(vararg playerModel: PlayerModel): Flow<Response<Unit?>> {
        return flow {
            playerDao.insert(*playerModel)
            emit(Response.success(null))
        }.catch {
            emit(Response.error())
        }.flowOn(getCoroutineContext())
    }

    suspend fun getPlayerDetailsFlow(playerId: Int): Flow<Response<PlayerModel?>> {
        return flowOf(playerDao.findPlayerById(playerId))
            .map { Response.success(it) }
            .catch { emit(Response.error()) }
            .flowOn(getCoroutineContext())
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

    suspend fun deletePlayer(user: PlayerModel): Flow<Response<Unit?>> {
        return flow {
            playerDao.delete(user)
            emit(Response.success(null))
        }.catch { emit(Response.error()) }.flowOn(getCoroutineContext())
    }

    private fun getCoroutineContext(): CoroutineContext = coroutineContextProvider?.IO ?: Dispatchers.IO
}