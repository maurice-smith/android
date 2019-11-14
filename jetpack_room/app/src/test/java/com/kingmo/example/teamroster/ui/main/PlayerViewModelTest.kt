package com.kingmo.example.teamroster.ui.main

import com.kingmo.example.teamroster.database.PlayerDao
import com.kingmo.example.teamroster.viewmodels.PlayerViewModel
import org.junit.After
import org.junit.Before

import org.mockito.Mock
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class PlayerViewModelTest {
//    @Rule
//    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var playerDao: PlayerDao

    private lateinit var viewModel: PlayerViewModel

    @Before
    fun setUp() {
        //`when`(playerDao.findPlayerById(any())).thenReturn()
        viewModel = PlayerViewModel(playerDao)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun shouldInvokeGetPlayers() {
        viewModel.getPlayers()

        verify(playerDao).getPlayers()
    }
}