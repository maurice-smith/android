package com.mo.jetpack.hiltdemo.model

import javax.inject.Inject

class Person @Inject constructor(private val name: NameModel,
                                 private val dateOfBirthModel: DateOfBirthModel) {
    fun getInfo(): String {
        return "Name: ${name.firstName} ${name.lastName}\n Birthday: ${dateOfBirthModel.getBirthDay()}"
    }
}