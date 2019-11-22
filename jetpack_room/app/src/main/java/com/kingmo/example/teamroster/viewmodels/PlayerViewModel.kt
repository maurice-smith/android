package com.kingmo.example.teamroster.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel
import com.kingmo.example.teamroster.BR
import com.kingmo.example.teamroster.R
import com.kingmo.example.teamroster.database.Player
import com.kingmo.example.teamroster.view.adapters.AdapterItemViewModel

class PlayerViewModel(private val player: Player): AdapterItemViewModel, ViewModel() {

    fun getPlayerId(): Int = player.playerId

    fun getPlayerBio(): String? = player.bio

    fun getBioVisibility(): Int = getVisibilityForStringProperty(player.bio)

    fun getFirstName(): String = player.firstName

    fun getLastName(): String = player.lastName

    fun getJerseyNumber(): String? = player.jerseyNumber

    fun getJerseyNumberVisibility(): Int = getVisibilityForStringProperty(player.jerseyNumber)

    fun getRosterPosition(): String = player.position

    fun getPlayerPhotoUrl(): String? = player.photoUrl

    fun getPlayerPhotoVisibility(): Int = if (!player.photoUrl?.trim().isNullOrEmpty()) View.VISIBLE else View.INVISIBLE

    override fun getLayoutResId(): Int = R.layout.player_item_view

    override fun getBindingVariable(): Int = BR.playerViewModel

    private fun getVisibilityForStringProperty(stringArg: String?) = if (!stringArg?.trim().isNullOrEmpty()) View.VISIBLE else View.GONE
}