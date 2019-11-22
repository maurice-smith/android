package com.kingmo.example.teamroster.ui.main

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kingmo.example.teamroster.database.Player
import com.kingmo.example.teamroster.database.PlayerDao
import com.kingmo.example.teamroster.utils.DEFAULT_ERROR_MSG
import com.kingmo.example.teamroster.utils.EMPTY_STRING
import com.kingmo.example.teamroster.utils.schedulers.TestSchedulerProvider
import com.kingmo.example.teamroster.view.AddPlayerInfoClickListener
import com.kingmo.example.teamroster.viewmodels.ErrorViewModel
import com.kingmo.example.teamroster.viewmodels.PlayerInfoFormViewModel
import com.kingmo.example.teamroster.viewmodels.PlayerViewModel
import com.kingmo.example.teamroster.viewmodels.RosterViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class RosterViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var playerDao: PlayerDao

    @Mock
    private lateinit var playerInfoClickListener: AddPlayerInfoClickListener

    private lateinit var viewModel: RosterViewModel

    @Before
    fun setUp() {
        viewModel = RosterViewModel(playerDao, TestSchedulerProvider())
    }

    @After
    fun tearDown() {
    }

    @Test
    fun shouldLoadPlayersOnSuccessWithPlayers() {
        `when`(playerDao.getPlayers()).thenReturn(Observable.just(listOf(Player(playerId = 123, firstName = "Paul", lastName = "Wall"))))

        viewModel.loadPlayers()

        val playerList: List<PlayerViewModel>? = viewModel.playersLiveData.value as List<PlayerViewModel>

        assertEquals(View.GONE, viewModel.noPlayersFoundVisibility.value)
        assertEquals(View.VISIBLE, viewModel.playerRosterVisibility.value)

        assertTrue(playerList!!.isNotEmpty())
        assertEquals(123, playerList.get(0).getPlayerId())
        assertEquals("Paul", playerList.get(0).getFirstName())
        assertEquals("Wall", playerList.get(0).getLastName())

        verify(playerDao).getPlayers()
    }

    @Test
    fun shouldLoadPlayersOnSuccessWithoutPlayers() {
        `when`(playerDao.getPlayers()).thenReturn(Observable.just(emptyList()))

        viewModel.loadPlayers()

        assertNull(viewModel.playersLiveData.value)
        assertEquals(View.VISIBLE, viewModel.noPlayersFoundVisibility.value)
        assertEquals(View.GONE, viewModel.playerRosterVisibility.value)

        verify(playerDao).getPlayers()
    }

    @Test
    fun shouldNotLoadPlayersWhenErrorOccurs() {
        `when`(playerDao.getPlayers()).thenReturn(Observable.error(Exception("Error Message.")))

        viewModel.loadPlayers()

        assertNull(viewModel.playersLiveData.value)
        assertEquals(View.GONE, viewModel.noPlayersFoundVisibility.value)
        assertEquals(View.GONE, viewModel.playerRosterVisibility.value)

        val errorViewModel: ErrorViewModel? = viewModel.errorViewModel.value
        assertEquals("Error Message.", errorViewModel?.message)
        assertEquals(View.VISIBLE, errorViewModel?.errorVisibility)

        verify(playerDao).getPlayers()
    }

    @Test
    fun shouldShowDefaultErrorMessageOnLoadPlayersError() {
        `when`(playerDao.getPlayers()).thenReturn(Observable.error(Exception(EMPTY_STRING)))

        viewModel.loadPlayers()

        val errorViewModel: ErrorViewModel? = viewModel.errorViewModel.value
        assertEquals(DEFAULT_ERROR_MSG, errorViewModel?.message)
        assertEquals(View.VISIBLE, errorViewModel?.errorVisibility)
    }

    @Test
    fun shouldAddPlayerOnSuccess() {
        `when`(playerDao.insert(Player())).thenReturn(Completable.complete())

        viewModel.addPlayer(PlayerInfoFormViewModel(), playerInfoClickListener)

        verify(playerInfoClickListener).onPlayerAddedSuccess()
    }

    @Test
    fun shouldShowAddPlayerError() {
        `when`(playerDao.insert(Player())).thenReturn(Completable.error(Exception("ERROR")))

        viewModel.addPlayer(PlayerInfoFormViewModel(), playerInfoClickListener)

        val errorViewModel: ErrorViewModel? = viewModel.errorViewModel.value
        assertEquals("ERROR", errorViewModel?.message)
        assertEquals(View.VISIBLE, errorViewModel?.errorVisibility)

        verifyZeroInteractions(playerInfoClickListener)
    }
}