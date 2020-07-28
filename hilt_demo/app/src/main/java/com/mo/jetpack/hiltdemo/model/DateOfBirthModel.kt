package com.mo.jetpack.hiltdemo.model

import java.time.Month
import javax.inject.Inject

class DateOfBirthModel @Inject constructor(private val month: Month, private val day: Int, private val year: Int) {
    fun getBirthDay(): String {
        return "${month.name} $day, $year"
    }
}