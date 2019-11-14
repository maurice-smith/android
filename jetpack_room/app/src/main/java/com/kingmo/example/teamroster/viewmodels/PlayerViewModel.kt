package com.kingmo.example.teamroster.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kingmo.example.teamroster.database.Player
import com.kingmo.example.teamroster.database.PlayerDao

class PlayerViewModel(private val playerDao: PlayerDao): ViewModel() {

    fun getPlayers(): LiveData<List<Player>> {
        return playerDao.getPlayers()
    }

    fun addPlayer(playerToAdd: Player) {
        playerDao.insert(playerToAdd)
    }

    fun removePlayer(playerToRemove: Player) {
        playerDao.delete(playerToRemove)
    }
}
