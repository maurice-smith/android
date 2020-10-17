package com.mo.jetpack.hiltdemo.di

import com.mo.jetpack.hiltdemo.repo.PersonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object AppModule {

    @Provides
    fun providesPersonRepository() = PersonRepository()
}