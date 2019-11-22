package com.kingmo.example.teamroster.view

interface RosterClickListener {
    fun onAddPlayerClick()
    fun onPlayerRemoved()
}

interface AddPlayerInfoClickListener {
    fun onAddPlayer()
    fun onPlayerAddedSuccess()
}

class RosterHandler(private val rosterClickListener: RosterClickListener) {
    fun goToAddPlayerScreen() = rosterClickListener.onAddPlayerClick()
}

class AddPlayerHandler(private val addPlayerInfoClickListener: AddPlayerInfoClickListener) {
    fun addPlayer() {
        addPlayerInfoClickListener.onAddPlayer()
    }
}