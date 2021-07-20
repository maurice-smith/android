package com.kingdom.util.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CoroutineDispatcherProvider {
    fun getMain(): CoroutineDispatcher
    fun getIo(): CoroutineDispatcher
}

class AppCoroutineDispatcherProvider: CoroutineDispatcherProvider {
    override fun getMain(): CoroutineDispatcher = Dispatchers.Main
    override fun getIo(): CoroutineDispatcher = Dispatchers.IO
}