package com.kingmo.example.teamroster.view.adapters

import com.kingmo.example.teamroster.viewmodels.PlayerViewModel

class PlayersRecyclerAdapter(
    private val playerViewModels: List<PlayerViewModel>,
    private val itemClickListener: ItemClickListener? = null
) : BaseRecyclerAdapter(playerViewModels, itemClickListener) {

    fun updateViewModels(updatePlayerViewModels: List<PlayerViewModel>) {
        itemViewModelList = updatePlayerViewModels
        notifyDataSetChanged()
    }
}

