package com.kingmo.example.teamroster.viewmodels

import android.view.View
import com.kingmo.example.teamroster.utils.EMPTY_STRING
import org.junit.Assert.*
import org.junit.Test

class ErrorViewModelTest {
    private lateinit var errorViewModel: ErrorViewModel

    @Test
    fun shouldReturnMessageVisible() {
        errorViewModel = ErrorViewModel("Error")
        assertEquals(View.VISIBLE, errorViewModel.getErrorVisibility())
    }

    @Test
    fun shouldReturnMessageGone() {
        errorViewModel = ErrorViewModel(EMPTY_STRING)
        assertEquals(View.GONE, errorViewModel.getErrorVisibility())
    }
}