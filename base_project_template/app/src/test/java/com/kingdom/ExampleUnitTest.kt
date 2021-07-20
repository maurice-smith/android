package com.kingdom

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import testutil.CoroutineTestRule


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class ExampleUnitTest {
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun flowTestExample() = coroutineTestRule.dispatcher.runBlockingTest {
        val testFlow = flowOf(listOf("Mike", "Paul"))

        assertThat(testFlow.first()[0]).isEqualTo("Mike")
    }
}