
package com.kingmo.example.teamroster.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kingmo.example.teamroster.R
import com.kingmo.example.teamroster.databinding.MainFragmentBinding
import com.kingmo.example.teamroster.view.*
import com.kingmo.example.teamroster.view.adapters.AdapterItemViewModel
import com.kingmo.example.teamroster.view.adapters.ItemClickListener
import com.kingmo.example.teamroster.view.adapters.PlayersRecyclerAdapter
import com.kingmo.example.teamroster.viewmodels.PlayerViewModel
import com.kingmo.example.teamroster.viewmodels.RosterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), RosterListener, ItemClickListener {

    private val rosterViewModel: RosterViewModel by viewModels()
    private lateinit var playersRecyclerAdapter: PlayersRecyclerAdapter
    private lateinit var fragBinding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        fragBinding.setLifecycleOwner { this.lifecycle }
        return fragBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragBinding.rosterHandler = RosterHandler(this)
        fragBinding.rosterViewModel = rosterViewModel

        val rosterList: RecyclerView = fragBinding.rosterList
        rosterList.layoutManager = LinearLayoutManager(context)
        playersRecyclerAdapter = PlayersRecyclerAdapter(mutableListOf(), this)
        rosterList.adapter = playersRecyclerAdapter

        rosterViewModel.playersLiveData.observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToNoResultsFragment())
            } else {
                (rosterList.adapter as PlayersRecyclerAdapter).updateViewModels(it)
            }
        })

        rosterViewModel.loadPlayers()
    }

    override fun onRemovePlayerSuccess() {
        //no-op
    }

    override fun goToAddScreen() {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddPlayerFragment())
    }

    override fun doItemAction(itemViewModel: AdapterItemViewModel) {
        val playerViewModel: PlayerViewModel = itemViewModel as PlayerViewModel
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToPlayerDetialsFragment(playerViewModel.getPlayerId()))
    }

    override fun removeItem(itemViewModel: AdapterItemViewModel) {
        val playerViewModel: PlayerViewModel = itemViewModel as PlayerViewModel
        val successDialog: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        successDialog.setCancelable(false)
            .setMessage(resources.getString(R.string.delete_player_confirmation_msg, playerViewModel.getFirstName(), playerViewModel.getLastName()))
            .setPositiveButton(R.string.yes) { _, _ ->  rosterViewModel.removePlayer(playerViewModel) }
            .setNegativeButton(R.string.no, null)
            .show()
    }
}
