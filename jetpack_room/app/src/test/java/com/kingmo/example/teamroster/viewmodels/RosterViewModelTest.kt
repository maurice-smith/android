package com.kingmo.example.teamroster.viewmodels

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.kingmo.example.teamroster.database.PlayerModel
import com.kingmo.example.teamroster.models.Response
import com.kingmo.example.teamroster.repository.PlayerRepo
import com.kingmo.example.teamroster.utils.TestCoroutineContextProvider
import com.kingmo.example.teamroster.view.AddPlayerListener
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyBlocking
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/*
* TODO: figure out how to unit test coroutines: how to mock deps that have suspended functions.
*/
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RosterViewModelTest {
    // This Rule allows LiveData & other Jetpack components to execute synchronously
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var addPlayerListener: AddPlayerListener

    private var playerRepoSuccessStub = mock<PlayerRepo> {
        onBlocking { getPlayersAsync() } doReturn CompletableDeferred(Response.success(listOf(PlayerModel(playerId = 123, firstName = "Paul", lastName = "Wall"))))
    }

    private lateinit var viewModel: RosterViewModel

    private val testCoroutineContextProvider = TestCoroutineContextProvider()

    private fun setupRosterViewModel(mockPlayerRepo: PlayerRepo) {
        viewModel = RosterViewModel(playerRepo = mockPlayerRepo,
            savedStateHandle = SavedStateHandle(),
            coroutineScope = TestCoroutineScope(testCoroutineContextProvider.testCoroutineDispatcher))
    }

    @Before
    fun setUp() {

        Dispatchers.setMain(testCoroutineContextProvider.testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testCoroutineContextProvider.testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun shouldLoadPlayersOnSuccessWithPlayers() {
        setupRosterViewModel(playerRepoSuccessStub)
        viewModel.loadPlayers()
        testCoroutineContextProvider.testCoroutineDispatcher.advanceUntilIdle()

        val playerList: List<PlayerViewModel> = viewModel.playersLiveData.value as List<PlayerViewModel>

        assertEquals(View.GONE, viewModel.noPlayersFoundVisibility.value)
        assertEquals(View.VISIBLE, viewModel.playerRosterVisibility.value)
        assertEquals(View.GONE, viewModel.progressBarVisibility.value)

        assertTrue(playerList.isNotEmpty())
        assertEquals(123, playerList.first().getPlayerId())
        assertEquals("Paul", playerList.first().getFirstName())
        assertEquals("Wall", playerList.first().getLastName())
    }

    @Test
    fun shouldNotLoadPlayersWhenErrorOccurs() {
        playerRepoSuccessStub = mock {
            onBlocking { getPlayersAsync() } doReturn CompletableDeferred(Response.error())
        }
        setupRosterViewModel(playerRepoSuccessStub)
        viewModel.loadPlayers()
        testCoroutineContextProvider.testCoroutineDispatcher.advanceUntilIdle()

        assertTrue(viewModel.playersLiveData.value!!.isEmpty())
        assertEquals(View.VISIBLE, viewModel.noPlayersFoundVisibility.value)
        assertEquals(View.GONE, viewModel.playerRosterVisibility.value)
    }

    @Test
    fun shouldAddPlayerOnSuccess() {
        playerRepoSuccessStub = mock {
            onBlocking { insertPlayer() }
        }


        setupRosterViewModel(playerRepoSuccessStub)
        viewModel.addPlayer(PlayerInfoFormViewModel(), addPlayerListener)
        testCoroutineContextProvider.testCoroutineDispatcher.advanceUntilIdle()

        verifyBlocking(playerRepoSuccessStub) { insertPlayer() }
    }

//    @Test
//    fun shouldShowAddPlayerError() {
//        `when`(playerDao.insert(PlayerModel())).thenReturn(Completable.error(Exception("ERROR")))
//
//        viewModel.addPlayer(PlayerInfoFormViewModel(), addPlayerListener)
//
//        val errorViewModel: ErrorViewModel? = viewModel.errorViewModel.value
//        assertEquals("ERROR", errorViewModel?.message)
//        assertEquals(View.VISIBLE, errorViewModel?.errorVisibility)
//
//        verifyNoInteractions(addPlayerListener)
//        verify(playerDao).insert(PlayerModel())
//    }
}