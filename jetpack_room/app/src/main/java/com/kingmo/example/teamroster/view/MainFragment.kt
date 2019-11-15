
package com.kingmo.example.teamroster.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kingmo.example.teamroster.R
import com.kingmo.example.teamroster.RosterApplication
import com.kingmo.example.teamroster.database.RosterAppDatabase
import com.kingmo.example.teamroster.view.adapters.PlayersRecyclerAdapter
import com.kingmo.example.teamroster.viewmodels.AppViewModelFactory
import com.kingmo.example.teamroster.viewmodels.RosterViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: RosterViewModel
    private lateinit var rootView: View
    private lateinit var rosterDb: RosterAppDatabase
    private lateinit var playersRecyclerAdapter: PlayersRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rosterDb = (activity?.applicationContext as RosterApplication).getAppDataBase()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        rootView = inflater.inflate(R.layout.main_fragment, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val appViewModelFactory: AppViewModelFactory = AppViewModelFactory(rosterDb.getPlayerDao())
        val rosterList: RecyclerView = rootView.findViewById(R.id.roster_list)
        rosterList.layoutManager = LinearLayoutManager(context)
        playersRecyclerAdapter = PlayersRecyclerAdapter(mutableListOf())

        viewModel = ViewModelProviders.of(this, appViewModelFactory).get(RosterViewModel::class.java)

        viewModel.getPlayers().observe(this, Observer {playersRecyclerAdapter.updateViewModels(it)})

        viewModel.getError().observe(this, Observer { Log.e("**ERROR", it.errorMessage) })
    }

}
