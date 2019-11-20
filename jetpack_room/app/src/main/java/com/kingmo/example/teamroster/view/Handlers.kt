package com.kingmo.example.teamroster.view

interface RosterClickListener {
    fun onAddPlayerClick()
}

class RosterHandler(private val rosterClickListener: RosterClickListener) {
    fun goToAddPlayerScreen() = rosterClickListener.onAddPlayerClick()
}