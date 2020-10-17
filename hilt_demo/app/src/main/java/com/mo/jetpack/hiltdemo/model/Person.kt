package com.mo.jetpack.hiltdemo.model

class Person(private val name: NameModel,
             private val dateOfBirthModel: DateOfBirthModel) {
    fun getInfo(): String {
        return "Name: ${name.firstName} ${name.lastName}\nBirthday: ${dateOfBirthModel.getBirthDay()}"
    }
}