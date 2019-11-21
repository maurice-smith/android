package com.kingmo.example.teamroster.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kingmo.example.teamroster.database.PlayerDao
import com.kingmo.example.teamroster.viewmodels.RosterViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule

import org.mockito.Mock
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class RosterViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var playerDao: PlayerDao

    private lateinit var viewModel: RosterViewModel

    @Before
    fun setUp() {
        //`when`(playerDao.findPlayerById(any())).thenReturn()
        viewModel = RosterViewModel(playerDao)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun shouldInvokeGetPlayers() {
        viewModel.loadPlayers()

        verify(playerDao).getPlayers()
    }
}