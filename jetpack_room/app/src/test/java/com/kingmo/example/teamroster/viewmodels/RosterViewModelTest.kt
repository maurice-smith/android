package com.kingmo.example.teamroster.viewmodels

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.kingmo.example.teamroster.database.PlayerDao
import com.kingmo.example.teamroster.database.PlayerModel
import com.kingmo.example.teamroster.models.Response
import com.kingmo.example.teamroster.repository.PlayerRepo
import com.kingmo.example.teamroster.utils.DEFAULT_ERROR_MSG
import com.kingmo.example.teamroster.utils.EMPTY_STRING
import com.kingmo.example.teamroster.utils.TestCoroutineContextProvider
import com.kingmo.example.teamroster.view.AddPlayerListener
import com.kingmo.example.teamroster.view.RosterListener
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.stub
import io.reactivex.Completable
import io.reactivex.Observable
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
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

    @Mock
    private lateinit var rosterListener: RosterListener

    @Mock
    private lateinit var playerDao: PlayerDao

    @Mock
    private lateinit var playerRepo: PlayerRepo

    private lateinit var viewModel: RosterViewModel

    private val testCoroutineContextProvider = TestCoroutineContextProvider()

    @Before
    fun setUp() {

        Dispatchers.setMain(testCoroutineContextProvider.testCoroutineDispatcher)

        viewModel = RosterViewModel(playerRepo = playerRepo,
            savedStateHandle = SavedStateHandle(),
            coroutineScope = TestCoroutineScope(testCoroutineContextProvider.testCoroutineDispatcher))
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        testCoroutineContextProvider.testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun shouldLoadPlayersOnSuccessWithPlayers() {
        runBlockingTest(testCoroutineContextProvider.testCoroutineDispatcher) {
            //`when`(playerDao.getPlayers()).thenReturn(listOf(PlayerModel(playerId = 123, firstName = "Paul", lastName = "Wall")))
            //`when`(playerRepo.getPlayersAsync()).thenReturn(CompletableDeferred(Response.success(listOf(PlayerModel(playerId = 123, firstName = "Paul", lastName = "Wall")))))

            playerRepo.stub {
                onBlocking { getPlayersAsync() }.doReturn(CompletableDeferred(Response.success(listOf(PlayerModel(playerId = 123, firstName = "Paul", lastName = "Wall")))))
            }
        }




        //testCoroutineContextProvider.testCoroutineDispatcher.advanceUntilIdle()

        viewModel.loadPlayers()
        testCoroutineContextProvider.testCoroutineDispatcher.advanceUntilIdle()

        val playerList: List<PlayerViewModel> = viewModel.playersLiveData.value as List<PlayerViewModel>

        assertEquals(View.GONE, viewModel.noPlayersFoundVisibility.value)
        assertEquals(View.VISIBLE, viewModel.playerRosterVisibility.value)

        assertTrue(playerList.isNotEmpty())
        assertEquals(123, playerList.first().getPlayerId())
        assertEquals("Paul", playerList.first().getFirstName())
        assertEquals("Wall", playerList.first().getLastName())
    }

//    @Test
//    fun shouldLoadPlayersOnSuccessWithoutPlayers() {
//        `when`(playerDao.getPlayersAsync()).thenReturn(Observable.just(emptyList()))
//
//        viewModel.loadPlayers()
//
//        assertNull(viewModel.playersLiveData.value)
//        assertEquals(View.VISIBLE, viewModel.noPlayersFoundVisibility.value)
//        assertEquals(View.GONE, viewModel.playerRosterVisibility.value)
//
//        verify(playerDao).getPlayersAsync()
//    }
//
//    @Test
//    fun shouldNotLoadPlayersWhenErrorOccurs() {
//        `when`(playerDao.getPlayersAsync()).thenReturn(Observable.error(Exception("Error Message.")))
//
//        viewModel.loadPlayers()
//
//        assertNull(viewModel.playersLiveData.value)
//        assertEquals(View.GONE, viewModel.noPlayersFoundVisibility.value)
//        assertEquals(View.GONE, viewModel.playerRosterVisibility.value)
//
//        val errorViewModel: ErrorViewModel? = viewModel.errorViewModel.value
//        assertEquals("Error Message.", errorViewModel?.message)
//        assertEquals(View.VISIBLE, errorViewModel?.errorVisibility)
//
//        verify(playerDao).getPlayersAsync()
//    }
//
//    @Test
//    fun shouldShowDefaultErrorMessageOnLoadPlayersError() {
//        `when`(playerDao.getPlayersAsync()).thenReturn(Observable.error(Exception(EMPTY_STRING)))
//
//        viewModel.loadPlayers()
//
//        val errorViewModel: ErrorViewModel? = viewModel.errorViewModel.value
//        assertEquals(DEFAULT_ERROR_MSG, errorViewModel?.message)
//        assertEquals(View.VISIBLE, errorViewModel?.errorVisibility)
//    }
//
//    @Test
//    fun shouldAddPlayerOnSuccess() {
//        `when`(playerDao.insert(PlayerModel())).thenReturn(Completable.complete())
//
//        viewModel.addPlayer(PlayerInfoFormViewModel(), addPlayerListener)
//
//        verify(addPlayerListener).onPlayerAddedSuccess()
//        verify(playerDao).insert(PlayerModel())
//    }
//
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
//
//    @Test
//    fun shouldRemovePlayerSuccess() {
//        val playerToRemove = PlayerModel()
//        `when`(playerDao.delete(playerToRemove)).thenReturn(Completable.complete())
//
//        viewModel.removePlayer(PlayerViewModel(playerToRemove), rosterListener)
//
//        verify(playerDao).delete(PlayerModel())
//        verify(rosterListener).onRemovePlayerSuccess()
//    }
//
//    @Test
//    fun shouldRemovePlayerError() {
//        val playerToRemove = PlayerModel()
//        `when`(playerDao.delete(playerToRemove)).thenReturn(Completable.error(Exception("ERROR1")))
//
//        viewModel.removePlayer(PlayerViewModel(playerToRemove), rosterListener)
//
//        val errorViewModel: ErrorViewModel? = viewModel.errorViewModel.value
//        assertEquals("ERROR1", errorViewModel?.message)
//        assertEquals(View.VISIBLE, errorViewModel?.errorVisibility)
//
//        verify(playerDao).delete(PlayerModel())
//    }
}