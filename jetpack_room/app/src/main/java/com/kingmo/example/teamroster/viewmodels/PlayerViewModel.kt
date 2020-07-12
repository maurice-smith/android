package com.kingmo.example.teamroster.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel
import com.kingmo.example.teamroster.BR
import com.kingmo.example.teamroster.R
import com.kingmo.example.teamroster.database.PlayerModel
import com.kingmo.example.teamroster.view.adapters.AdapterItemViewModel

class PlayerViewModel(private val playerModel: PlayerModel): AdapterItemViewModel, ViewModel() {

    fun getPlayerId(): Int = playerModel.playerId

    fun getPlayerBio(): String? = playerModel.bio

    fun getBioVisibility(): Int = getVisibilityForStringProperty(playerModel.bio)

    fun getFirstName(): String = playerModel.firstName

    fun getLastName(): String = playerModel.lastName

    fun getJerseyNumber(): String? = playerModel.jerseyNumber

    fun getJerseyNumberVisibility(): Int = getVisibilityForStringProperty(playerModel.jerseyNumber)

    fun getRosterPosition(): String = playerModel.position

    fun getPlayerPhotoUrl(): String? = playerModel.photoUrl

    fun getPlayerPhotoVisibility(): Int = if (!playerModel.photoUrl?.trim().isNullOrEmpty()) View.VISIBLE else View.INVISIBLE

    override fun getLayoutResId(): Int = R.layout.player_item_view

    override fun getBindingVariable(): Int = BR.playerViewModel

    private fun getVisibilityForStringProperty(stringArg: String?) = if (!stringArg?.trim().isNullOrEmpty()) View.VISIBLE else View.GONE
}