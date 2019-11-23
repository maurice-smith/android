package com.kingmo.example.teamroster.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.kingmo.example.teamroster.R
import com.kingmo.example.teamroster.RosterApplication
import com.kingmo.example.teamroster.database.RosterAppDatabase
import com.kingmo.example.teamroster.databinding.ActivityPlayerDetailsBinding
import com.kingmo.example.teamroster.utils.schedulers.AppScheduleProvider
import com.kingmo.example.teamroster.viewmodels.AppViewModelFactory
import com.kingmo.example.teamroster.viewmodels.RosterViewModel

class PlayerDetailsActivity : AppCompatActivity() {
    private lateinit var rosterViewModel: RosterViewModel
    private lateinit var rosterDb: RosterAppDatabase
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

        val inComingPlayerId: Int = intent.getIntExtra(PLAYER_ID_EXTRA, PLAYER_ID_DEFAULT)

        Log.d(TAG, "Started with Player_id: $inComingPlayerId")

        rosterDb = (applicationContext as RosterApplication).getAppDataBase()
        val appViewModelFactory = AppViewModelFactory(rosterDb.getPlayerDao(), AppScheduleProvider())
        rosterViewModel = ViewModelProviders.of(this, appViewModelFactory).get(RosterViewModel::class.java)

        detailsBinding.rosterViewModel = rosterViewModel

        rosterViewModel.loadPlayerDetails(inComingPlayerId)
    }
}
