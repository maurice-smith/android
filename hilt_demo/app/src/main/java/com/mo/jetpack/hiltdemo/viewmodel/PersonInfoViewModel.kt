package com.mo.jetpack.hiltdemo.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mo.jetpack.hiltdemo.repo.PersonRepository

class PersonInfoViewModel @ViewModelInject constructor(private val personRepo: PersonRepository,
                                                      @Assisted private val savedStateHandle: SavedStateHandle): ViewModel() {
    private val info: MutableLiveData<String> = MutableLiveData()

    fun getPersonalInfo(): LiveData<String> {
        info.postValue(personRepo.getPersonInfo())
        //savedStateHandle.set("", info)
        return info
    }
}