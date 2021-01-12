package com.kingmo.example.teamroster.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

fun ViewModel.getViewModelScope(scope: CoroutineScope?) = scope ?: this.viewModelScope