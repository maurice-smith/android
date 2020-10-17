package com.mo.jetpack.hiltdemo.model

import java.time.Month

class DateOfBirthModel(private val month: Month, private val day: Int, private val year: Int) {
    fun getBirthDay(): String {
        return "${month.name} $day, $year"
    }
}