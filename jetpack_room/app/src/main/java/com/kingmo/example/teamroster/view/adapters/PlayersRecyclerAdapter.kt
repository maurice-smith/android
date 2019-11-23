package com.kingmo.example.teamroster.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.kingmo.example.teamroster.R
import com.kingmo.example.teamroster.viewmodels.PlayerViewModel

class PlayersRecyclerAdapter(
    private val playerViewModels: List<PlayerViewModel>,
    private val itemClickListener: ItemClickListener? = null
) : BaseRecyclerAdapter(playerViewModels, itemClickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val viewBinding: ViewDataBinding =
            DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
        return PlayerItemViewHolder(
            viewBinding,
            itemClickListener
        )
    }

    fun updateViewModels(updatePlayerViewModels: List<PlayerViewModel>) {
        itemViewModelList = updatePlayerViewModels
        notifyDataSetChanged()
    }

    class PlayerItemViewHolder(
        private val itemBinding: ViewDataBinding,
        private val clickListener: ItemClickListener?
    ) : BaseViewHolder(itemBinding, clickListener) {
        override fun bind(itemViewModel: AdapterItemViewModel) {
            super.bind(itemViewModel)
            val removeItemView = itemBinding.root.findViewById<View>(R.id.delete_player)
            removeItemView?.setOnClickListener { clickListener?.removeItem(itemViewModel) }
        }
    }
}

