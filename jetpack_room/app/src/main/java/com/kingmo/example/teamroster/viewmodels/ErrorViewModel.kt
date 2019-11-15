package com.kingmo.example.teamroster.viewmodels

import android.view.View

class ErrorViewModel(val errorMessage: String) {
    fun getErrorVisibility(): Int = if (errorMessage.trim().isNotEmpty()) View.VISIBLE else View.GONE
}