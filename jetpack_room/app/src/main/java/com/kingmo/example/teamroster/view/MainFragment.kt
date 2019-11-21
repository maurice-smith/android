
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
import com.kingmo.example.teamroster.view.adapters.PlayersRecyclerAdapter
import com.kingmo.example.teamroster.viewmodels.AppViewModelFactory
import com.kingmo.example.teamroster.viewmodels.RosterViewModel

class MainFragment : Fragment(), RosterClickListener {

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
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        return fragBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragBinding.rosterHandler = RosterHandler(this)

        val appViewModelFactory = AppViewModelFactory(rosterDb.getPlayerDao())
        val rosterList: RecyclerView = fragBinding.rosterList
        rosterList.layoutManager = LinearLayoutManager(context)
        playersRecyclerAdapter = PlayersRecyclerAdapter(mutableListOf())

        rosterViewModel = ViewModelProviders.of(this, appViewModelFactory).get(RosterViewModel::class.java)
        rosterViewModel.getPlayers().observe(this, Observer {playersRecyclerAdapter.updateViewModels(it)})
    }

    override fun onAddPlayerClick() {
        startActivity(Intent(activity, AddPlayerActivity::class.java))
    }
}
