package com.kingdom.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kingdom.repository.GreetingRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import testutil.CoroutineTestRule

@ExperimentalCoroutinesApi
class HelloWorldViewModelTest {
    @get:Rule
    val coroutineRule = CoroutineTestRule()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val expectedValue = "Phone Home"
    private val mockRepo = mock< GreetingRepository> {
        runBlockingTest {
            whenever(it.getAlienGreeting()).thenReturn(expectedValue)
        }
    }

    private val viewModel = HelloWorldViewModel(mockRepo, coroutineRule.testScope)

    @Test
    fun shouldReturnAlienGreeting() {
        assertThat(viewModel.getAlienGreeting().value).isEqualTo(expectedValue)
    }
}