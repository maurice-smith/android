package com.mo.jetpack.hiltdemo.repo

import com.mo.jetpack.hiltdemo.model.DateOfBirthModel
import com.mo.jetpack.hiltdemo.model.NameModel
import com.mo.jetpack.hiltdemo.model.Person
import java.time.Month

class PersonRepository {
    fun getPersonInfo(): String = Person(getNameModel(), getDateOfBirthModel()).getInfo()

    private fun getDateOfBirthModel(): DateOfBirthModel = DateOfBirthModel(Month.FEBRUARY, 15, 1993)

    private fun getNameModel(): NameModel = NameModel("Mike", "Jones")
}