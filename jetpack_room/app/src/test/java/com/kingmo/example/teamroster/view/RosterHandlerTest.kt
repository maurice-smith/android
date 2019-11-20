package com.kingmo.example.teamroster.view

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RosterHandlerTest {

    @Mock
    lateinit var clickListener: RosterClickListener

    private lateinit var rosterHandler: RosterHandler

    @Before
    fun setUp() {
        rosterHandler = RosterHandler(clickListener)
    }

    @Test
    fun shouldInvokeGoToAddPlayerScreen() {
        rosterHandler.goToAddPlayerScreen()
        verify(clickListener).onAddPlayerClick()
    }
}