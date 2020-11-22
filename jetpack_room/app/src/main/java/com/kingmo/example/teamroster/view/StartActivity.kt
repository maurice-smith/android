package com.kingmo.example.teamroster.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kingmo.example.teamroster.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }
}