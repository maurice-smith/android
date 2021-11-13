package com.kingdom.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kingdom.repository.GreetingRepository
import com.kingdom.util.getViewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HelloWorldViewModel(private val greetingRepo: GreetingRepository,
                          coroutineScope: CoroutineScope? = null): ViewModel() {
    private val jobScope = getViewModelScope(coroutineScope)

    fun getAlienGreeting(): LiveData<String> {
        val output = MutableLiveData("")
        jobScope.launch {
            output.postValue(greetingRepo.getAlienGreeting())
        }
        return output
    }

}