package com.kingmo.example.teamroster.viewmodels

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kingmo.example.teamroster.database.PlayerModel
import com.kingmo.example.teamroster.database.PlayerDao
import com.kingmo.example.teamroster.utils.DEFAULT_ERROR_MSG
import com.kingmo.example.teamroster.utils.EMPTY_STRING
import com.kingmo.example.teamroster.utils.schedulers.TestSchedulerProvider
import com.kingmo.example.teamroster.view.PlayerInfoClickListener
import io.reactivex.Completable
import io.reactivex.Observable
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
    // This Rule allows LiveData & other Jetpack components to execute synchronously
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var playerDao: PlayerDao

    @Mock
    private lateinit var playerInfoClickListener: PlayerInfoClickListener

    private lateinit var viewModel: RosterViewModel

    @Before
    fun setUp() {
        viewModel = RosterViewModel(playerDao, TestSchedulerProvider())
    }

    @Test
    fun shouldLoadPlayersOnSuccessWithPlayers() {
        `when`(playerDao.getPlayers()).thenReturn(Observable.just(listOf(PlayerModel(playerId = 123, firstName = "Paul", lastName = "Wall"))))

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
        `when`(playerDao.insert(PlayerModel())).thenReturn(Completable.complete())

        viewModel.addPlayer(PlayerInfoFormViewModel(), playerInfoClickListener)

        verify(playerInfoClickListener).onPlayerAddedSuccess()
        verify(playerDao).insert(PlayerModel())
    }

    @Test
    fun shouldShowAddPlayerError() {
        `when`(playerDao.insert(PlayerModel())).thenReturn(Completable.error(Exception("ERROR")))

        viewModel.addPlayer(PlayerInfoFormViewModel(), playerInfoClickListener)

        val errorViewModel: ErrorViewModel? = viewModel.errorViewModel.value
        assertEquals("ERROR", errorViewModel?.message)
        assertEquals(View.VISIBLE, errorViewModel?.errorVisibility)

        verifyZeroInteractions(playerInfoClickListener)
        verify(playerDao).insert(PlayerModel())
    }

    @Test
    fun shouldRemovePlayerSuccess() {
        val playerToRemove = PlayerModel()
        `when`(playerDao.delete(playerToRemove)).thenReturn(Completable.complete())

        viewModel.removePlayer(PlayerViewModel(playerToRemove))

        verify(playerDao).delete(PlayerModel())
    }

    @Test
    fun shouldRemovePlayerError() {
        val playerToRemove = PlayerModel()
        `when`(playerDao.delete(playerToRemove)).thenReturn(Completable.error(Exception("ERROR1")))

        viewModel.removePlayer(PlayerViewModel(playerToRemove))

        val errorViewModel: ErrorViewModel? = viewModel.errorViewModel.value
        assertEquals("ERROR1", errorViewModel?.message)
        assertEquals(View.VISIBLE, errorViewModel?.errorVisibility)

        verify(playerDao).delete(PlayerModel())
    }

}