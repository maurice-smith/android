package testutil

import com.kingdom.util.coroutines.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

class TestDispatcherProvider {
    @ExperimentalCoroutinesApi
    class TestDispatcherProvider: CoroutineDispatcherProvider {
        private val dummyCoroutineDispatcher = TestCoroutineDispatcher()

        override fun getMain(): CoroutineDispatcher = dummyCoroutineDispatcher
        override fun getIo(): CoroutineDispatcher = dummyCoroutineDispatcher
    }
}