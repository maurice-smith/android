
package com.kingmo.example.teamroster.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kingmo.example.teamroster.AddPlayerActivity
import com.kingmo.example.teamroster.R
import com.kingmo.example.teamroster.RosterApplication
import com.kingmo.example.teamroster.database.RosterAppDatabase
import com.kingmo.example.teamroster.databinding.MainFragmentBinding
import com.kingmo.example.teamroster.utils.schedulers.AppScheduleProvider
import com.kingmo.example.teamroster.view.adapters.AdapterItemViewModel
import com.kingmo.example.teamroster.view.adapters.ItemClickListener
import com.kingmo.example.teamroster.view.adapters.PlayersRecyclerAdapter
import com.kingmo.example.teamroster.viewmodels.AppViewModelFactory
import com.kingmo.example.teamroster.viewmodels.PlayerViewModel
import com.kingmo.example.teamroster.viewmodels.RosterViewModel

class MainFragment : Fragment(), RosterClickListener, ItemClickListener {

    companion object {
        fun newInstance() = MainFragment()
        val TAG: String = MainFragment::class.java.simpleName
    }

    private lateinit var rosterViewModel: RosterViewModel
    private lateinit var rosterDb: RosterAppDatabase
    private lateinit var playersRecyclerAdapter: PlayersRecyclerAdapter
    private lateinit var fragBinding: MainFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rosterDb = (activity?.applicationContext as RosterApplication).getAppDataBase()
        val appViewModelFactory = AppViewModelFactory(rosterDb.getPlayerDao(), AppScheduleProvider())
        rosterViewModel = ViewModelProviders.of(this, appViewModelFactory).get(RosterViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        fragBinding.setLifecycleOwner { this.lifecycle }
        return fragBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragBinding.rosterHandler = RosterHandler(this)
        fragBinding.rosterViewModel = rosterViewModel


        val rosterList: RecyclerView = fragBinding.rosterList
        rosterList.layoutManager = LinearLayoutManager(context)
        playersRecyclerAdapter = PlayersRecyclerAdapter(mutableListOf(), this)
        rosterList.adapter = playersRecyclerAdapter

        rosterViewModel.playersLiveData.observe(this, Observer {
            (rosterList.adapter as PlayersRecyclerAdapter).updateViewModels(it)
        })

        rosterViewModel.loadPlayers()
    }

    override fun onAddPlayerClick() {
        startActivity(Intent(activity, AddPlayerActivity::class.java))
    }

    override fun onPlayerRemoved() {
        Log.d(TAG, "Player Removed.")
    }

    override fun doAction(itemViewModel: AdapterItemViewModel) {
        val playerViewModel: PlayerViewModel = itemViewModel as PlayerViewModel
        //TODO: take user to details
        //rosterViewModel.removePlayer(playerViewModel)
        Log.d(TAG, "Item Click id[${playerViewModel.getPlayerId()}] name[${playerViewModel.getFirstName()}]")
    }
}
