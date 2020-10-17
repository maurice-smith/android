package com.kingmo.example.teamroster.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kingmo.example.teamroster.R
import com.kingmo.example.teamroster.databinding.ActivityPlayerDetailsBinding
import com.kingmo.example.teamroster.viewmodels.RosterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerDetailsActivity : AppCompatActivity() {
    private val rosterViewModel: RosterViewModel by viewModels()
    //private lateinit var rosterDb: RosterAppDatabase
    private lateinit var detailsBinding: ActivityPlayerDetailsBinding

    companion object {
        val TAG: String = PlayerDetailsActivity::class.java.simpleName
        val PLAYER_ID_EXTRA: String = "$TAG.PLAYER_ID_EXTRA"
        const val PLAYER_ID_DEFAULT: Int = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_player_details)
        setContentView(detailsBinding.root)
        detailsBinding.lifecycleOwner = this

        val inComingPlayerId: Int = intent.getIntExtra(PLAYER_ID_EXTRA, PLAYER_ID_DEFAULT)

        Log.d(TAG, "Started with Player_id: $inComingPlayerId")

        //rosterDb = (applicationContext as RosterApplication).getAppDataBase()
        //val appViewModelFactory = AppViewModelFactory(rosterDb.getPlayerDao(), AppScheduleProvider())
        //rosterViewModel = ViewModelProviders.of(this, appViewModelFactory).get(RosterViewModel::class.java)

        detailsBinding.rosterViewModel = rosterViewModel

        //rosterViewModel.playerDetails.observe(this, Observer { detailsBinding.playerViewModel = it })

        rosterViewModel.loadPlayerDetails(inComingPlayerId)

    }
}
