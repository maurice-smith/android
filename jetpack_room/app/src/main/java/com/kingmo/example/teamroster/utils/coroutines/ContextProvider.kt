package com.kingmo.example.teamroster.utils.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

//https://www.ericdecanini.com/2020/04/06/unit-testing-coroutines-on-android/
open class CoroutineContextProvider @Inject constructor() {
    open val Main: CoroutineContext by lazy { Dispatchers.Main }
    open val IO: CoroutineContext by lazy { Dispatchers.IO }
}