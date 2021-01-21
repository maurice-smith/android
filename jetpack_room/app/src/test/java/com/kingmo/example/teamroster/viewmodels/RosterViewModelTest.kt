package com.kingmo.example.teamroster.viewmodels

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.kingmo.example.teamroster.database.PlayerModel
import com.kingmo.example.teamroster.models.Response
import com.kingmo.example.teamroster.repository.PlayerRepo
import com.kingmo.example.teamroster.utils.TestCoroutineContextProvider
import com.kingmo.example.teamroster.view.AddPlayerListener
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RosterViewModelTest {
    // This Rule allows LiveData & other Jetpack components to execute synchronously
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var addPlayerListener: AddPlayerListener

    private var playerRepoSuccessStub = mock<PlayerRepo> {
        onBlocking { getPlayersFlow() } doReturn flowOf(Response.success(listOf(PlayerModel(playerId = 123, firstName = "Paul", lastName = "Wall"))))
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
            onBlocking { getPlayersFlow() } doReturn flowOf(Response.error())
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
            onBlocking { insertPlayer(PlayerModel()) } doReturn flowOf(Response.success(null))
        }

        setupRosterViewModel(playerRepoSuccessStub)
        viewModel.addPlayer(PlayerInfoFormViewModel(), addPlayerListener)
        testCoroutineContextProvider.testCoroutineDispatcher.advanceUntilIdle()

        verifyBlocking(playerRepoSuccessStub) { insertPlayer(anyVararg()) }
        verify(addPlayerListener).onPlayerAddedSuccess()
    }

    @Test
    fun shouldShowAddPlayerError() {
        playerRepoSuccessStub = mock {
            onBlocking { insertPlayer(PlayerModel()) } doReturn flowOf(Response.error())
        }

        setupRosterViewModel(playerRepoSuccessStub)
        viewModel.addPlayer(PlayerInfoFormViewModel(), addPlayerListener)
        testCoroutineContextProvider.testCoroutineDispatcher.advanceUntilIdle()

        verifyBlocking(playerRepoSuccessStub) { insertPlayer(anyVararg()) }
        verify(addPlayerListener).onPlayerAddError()
    }

    @Test
    fun shouldRemovePlayerSuccess() {
        val removePlayerRepoStub: PlayerRepo = mock {
            onBlocking { getPlayersFlow() } doReturn flowOf(Response.success(emptyList()))
            onBlocking { deletePlayer(PlayerModel()) } doReturn flowOf(Response.success(null))
        }

        setupRosterViewModel(removePlayerRepoStub)
        viewModel.removePlayer(PlayerViewModel(PlayerModel()))
        testCoroutineContextProvider.testCoroutineDispatcher.advanceUntilIdle()

        verifyBlocking(removePlayerRepoStub) { getPlayersFlow() }
        verifyBlocking(removePlayerRepoStub) { deletePlayer(any()) }
    }

    @Test
    fun shouldRemovePlayerError() {
        val removePlayerRepoStub: PlayerRepo = mock {
            onBlocking { deletePlayer(PlayerModel()) } doReturn flowOf(Response.error())
        }

        setupRosterViewModel(removePlayerRepoStub)
        viewModel.removePlayer(PlayerViewModel(PlayerModel()))
        testCoroutineContextProvider.testCoroutineDispatcher.advanceUntilIdle()

        verifyBlocking(removePlayerRepoStub) { deletePlayer(any()) }
        verifyNoMoreInteractions(removePlayerRepoStub)
    }
}