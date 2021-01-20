package com.kingmo.example.teamroster.view


interface RosterListener {
    fun onRemovePlayerSuccess()
    fun goToAddScreen()
}

interface AddPlayerListener {
    fun onAddPlayerClick()
    fun onPlayerAddedSuccess()
    fun onPlayerAddError()
}

class RosterHandler(private val rosterListener: RosterListener) {
    fun goToAddPlayerScreen() = rosterListener.goToAddScreen()
}

class AddPlayerHandler(private val clickListener: AddPlayerListener) {
    fun addPlayer() = clickListener.onAddPlayerClick()
}