package com.kingmo.example.teamroster.utils

import com.kingmo.example.teamroster.utils.coroutines.CoroutineContextProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class TestCoroutineContextProvider: CoroutineContextProvider() {
    val testCoroutineDispatcher = TestCoroutineDispatcher()
    override val Main: CoroutineContext = testCoroutineDispatcher
    override val IO: CoroutineContext = testCoroutineDispatcher
}
