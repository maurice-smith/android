package com.kingdom.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

fun ViewModel.getViewModelScope(scope: CoroutineScope?) = scope ?: this.viewModelScope