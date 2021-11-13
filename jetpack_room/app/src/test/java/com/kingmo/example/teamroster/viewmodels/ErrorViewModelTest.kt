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

    @Test
    fun shouldReturnIndexOfSum() {
        val intArray = arrayOf(2,7,11,15)
        val targetSum = 9
        val outputIndexList = HashSet<Int>()
        val expectedIndexList = HashSet<Int>(listOf(0,1))

        for (index in intArray.indices) {
            for (j in 1 until intArray.size) {
                if ( intArray[index] + intArray[j] == targetSum) {
                    outputIndexList.add(index)
                    outputIndexList.add(j)
                    break;
                }
            }
        }

//        for (int i = 0; i < intArray.lenght -1;i++) {in
//            for (int j = 1; j <= intArray.lenght - 1;j++) {
//                if (intArray[i] + intArray[j] == targetSum) {
//                    outputSet.add(i)
//                    outputSet.add(j)
        //break
//                }
//        }
//        }

        assertEquals(expectedIndexList, outputIndexList)
    }
}