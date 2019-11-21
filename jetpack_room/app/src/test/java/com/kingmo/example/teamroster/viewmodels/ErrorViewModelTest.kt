package com.kingmo.example.teamroster.viewmodels

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kingmo.example.teamroster.utils.EMPTY_STRING
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ErrorViewModelTest {
    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var errorViewModel: ErrorViewModel

    @Before
    fun setUp() {
        errorViewModel = ErrorViewModel()
    }

    @Test
    fun shouldReturnMessageVisible() {
        errorViewModel.message = "Error"
        assertEquals(View.VISIBLE, errorViewModel.errorVisibility)
    }

    @Test
    fun shouldReturnMessageGone() {
        errorViewModel.message = EMPTY_STRING
        assertEquals(View.GONE, errorViewModel.errorVisibility)
    }

    @Test
    fun shouldReturnErrorMessage() {
        errorViewModel.message = "Error"
        assertEquals("Error", errorViewModel.message)
    }
}