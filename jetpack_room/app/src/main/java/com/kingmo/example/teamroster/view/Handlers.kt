package com.kingmo.example.teamroster.view

interface RosterClickListener {
    fun onAddPlayerClick()
}

interface PlayerInfoClickListener {
    fun onAddPlayer()
    fun onPlayerAddedSuccess()
}

class RosterHandler(private val rosterClickListener: RosterClickListener) {
    fun goToAddPlayerScreen() = rosterClickListener.onAddPlayerClick()
}

class AddPlayerHandler(private val playerInfoClickListener: PlayerInfoClickListener) {
    fun addPlayer() {
        playerInfoClickListener.onAddPlayer()
    }
}