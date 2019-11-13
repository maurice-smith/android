package com.kingmo.example.teamroster.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.kingmo.example.teamroster.database.Player
import com.kingmo.example.teamroster.database.PlayerDao
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.mockito.Mock
import org.junit.Rule
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