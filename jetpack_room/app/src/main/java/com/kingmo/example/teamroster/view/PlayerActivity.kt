package com.kingmo.example.teamroster.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kingmo.example.teamroster.R
import com.kingmo.example.teamroster.databinding.ActivityAddPlayerBinding
import com.kingmo.example.teamroster.viewmodels.PlayerInfoFormViewModel
import com.kingmo.example.teamroster.viewmodels.RosterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerActivity : AppCompatActivity(), PlayerInfoClickListener {

    private val rosterViewModel: RosterViewModel by viewModels()
    private lateinit var activityBinding: ActivityAddPlayerBinding
    private var playerFormViewModel: PlayerInfoFormViewModel? = null

    companion object {
        val TAG: String = PlayerActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_add_player
        )
        activityBinding.lifecycleOwner = this
        setContentView(activityBinding.root)

        //rosterDb = (applicationContext as RosterApplication).getAppDataBase()
        //val appViewModelFactory = AppViewModelFactory(rosterDb.getPlayerDao(), AppScheduleProvider())
        //rosterViewModel = ViewModelProviders.of(this, appViewModelFactory).get(RosterViewModel::class.java)

        activityBinding.playerFormViewModel = PlayerInfoFormViewModel()
        activityBinding.addHandler = AddPlayerHandler(this)
        playerFormViewModel = activityBinding.playerFormViewModel
    }

    override fun onAddPlayer() {
        val playerFormViewModel: PlayerInfoFormViewModel? = activityBinding.playerFormViewModel
        if (playerFormViewModel != null) {
            rosterViewModel.addPlayer(playerFormViewModel, this)
        } else {
            Log.e(TAG, "ERROR playerFormViewModel is null. no op.")
        }
    }

    override fun onPlayerAddedSuccess() {
        Toast.makeText(this,
            R.string.player_add_success_msg, Toast.LENGTH_SHORT).show()
        finish()
    }
}
