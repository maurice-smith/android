package com.mo.jetpack.hiltdemo.di

import com.mo.jetpack.hiltdemo.model.DateOfBirthModel
import com.mo.jetpack.hiltdemo.model.NameModel
import com.mo.jetpack.hiltdemo.model.Person
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import java.time.Month

@Module
@InstallIn(ActivityComponent::class)
object PersonModule {
    @Provides
    fun provideDateOfBirthModel(): DateOfBirthModel {
        return DateOfBirthModel(Month.FEBRUARY, 15, 1993)
    }

    @Provides
    fun provideNameModel(): NameModel {
        return NameModel("Mike", "Jones")
    }

//    @Provides
//    fun providesPersonModel() : Person {
//        return Person()
//    }
}