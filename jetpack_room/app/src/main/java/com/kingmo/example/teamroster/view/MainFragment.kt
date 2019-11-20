
package com.kingmo.example.teamroster.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private lateinit var viewModel: RosterViewModel
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

        viewModel = ViewModelProviders.of(this, appViewModelFactory).get(RosterViewModel::class.java)
        viewModel.getPlayers().observe(this, Observer {playersRecyclerAdapter.updateViewModels(it)})
        viewModel.getError().observe(this, Observer { Log.e(TAG, it.errorMessage) })
    }

    override fun onAddPlayerClick() {
        Log.d(TAG, "onAddPlayerClick - CLICKED")
    }
}
