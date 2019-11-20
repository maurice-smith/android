package com.kingmo.example.teamroster.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kingmo.example.teamroster.utils.EMPTY_STRING

class ErrorViewModel: ViewModel() {
    val errorVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    var message: String = EMPTY_STRING
        set(value) {
            errorMessage.postValue(value)
            errorVisibility.postValue(if (value.trim().isNotEmpty()) View.VISIBLE else View.GONE)
        }
}