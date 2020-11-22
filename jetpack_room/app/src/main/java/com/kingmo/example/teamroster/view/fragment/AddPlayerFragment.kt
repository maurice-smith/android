package com.kingmo.example.teamroster.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kingmo.example.teamroster.R
import com.kingmo.example.teamroster.databinding.FragmentAddPlayerBinding
import com.kingmo.example.teamroster.view.AddPlayerHandler
import com.kingmo.example.teamroster.view.PlayerInfoClickListener
import com.kingmo.example.teamroster.viewmodels.PlayerInfoFormViewModel
import com.kingmo.example.teamroster.viewmodels.RosterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPlayerFragment : Fragment(), PlayerInfoClickListener {
    private val rosterViewModel: RosterViewModel by viewModels()
    private lateinit var viewBinding: FragmentAddPlayerBinding
    private var playerFormViewModel: PlayerInfoFormViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_player, container, false)
        viewBinding.lifecycleOwner = this
        return viewBinding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.addHandler = AddPlayerHandler(this)
        playerFormViewModel = PlayerInfoFormViewModel()
        viewBinding.playerFormViewModel = playerFormViewModel
    }

    override fun onAddPlayer() {
        val playerFormViewModel: PlayerInfoFormViewModel? = viewBinding.playerFormViewModel
        if (playerFormViewModel != null) {
            rosterViewModel.addPlayer(playerFormViewModel, this)
        } else {
            Log.e(TAG, "ERROR playerFormViewModel is null. no op.")
        }
    }

    override fun onPlayerAddedSuccess() {
        Toast.makeText(context, R.string.player_add_success_msg, Toast.LENGTH_SHORT).show()
        findNavController().navigate(AddPlayerFragmentDirections.actionAddPlayerFragmentToMainFragment())
    }

    companion object {
        @JvmStatic
        val TAG: String = AddPlayerFragment::class.java.simpleName
    }
}