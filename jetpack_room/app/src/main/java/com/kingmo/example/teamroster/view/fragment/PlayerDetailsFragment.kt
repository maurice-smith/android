package com.kingmo.example.teamroster.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.kingmo.example.teamroster.R
import com.kingmo.example.teamroster.databinding.FragmentPlayerDetailsBinding
import com.kingmo.example.teamroster.viewmodels.RosterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerDetailsFragment : Fragment() {
    private val rosterViewModel: RosterViewModel by viewModels()
    private lateinit var detailsBinding: FragmentPlayerDetailsBinding
    private val navigationArgs: PlayerDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_player_details, container, false)
        detailsBinding.lifecycleOwner = this

        return detailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        detailsBinding.rosterViewModel = rosterViewModel
        rosterViewModel.loadPlayerDetails(navigationArgs.playerId)
    }
}