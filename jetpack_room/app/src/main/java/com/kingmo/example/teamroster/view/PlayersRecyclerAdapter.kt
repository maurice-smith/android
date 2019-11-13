package com.kingmo.example.teamroster.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kingmo.example.teamroster.database.Player

class PlayersRecyclerAdapter(val playerList: List<Player>): RecyclerView.Adapter<PlayersRecyclerAdapter.PlayerItemViewHolder>() {
    override fun onBindViewHolder(holder: PlayerItemViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerItemViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return playerList.size
    }

    class PlayerItemViewHolder(val itemView: View): RecyclerView.ViewHolder(itemView) {
    }
}

