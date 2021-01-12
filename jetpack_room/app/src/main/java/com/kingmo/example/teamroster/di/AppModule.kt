package com.kingmo.example.teamroster.di

import android.content.Context
import com.kingmo.example.teamroster.RosterApplication
import com.kingmo.example.teamroster.database.RosterAppDatabase
import com.kingmo.example.teamroster.repository.PlayerRepo
import com.kingmo.example.teamroster.utils.coroutines.CoroutineContextProvider
import com.kingmo.example.teamroster.utils.schedulers.AppScheduleProvider
import com.kingmo.example.teamroster.utils.schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRosterDatabase(@ApplicationContext context: Context): RosterAppDatabase {
        return (context as RosterApplication).getAppDataBase()
    }

    @Provides
    fun providesPlayerRepo(@ApplicationContext context: Context): PlayerRepo {
        return PlayerRepo(playerDao = provideRosterDatabase(context).playerDao)
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = AppScheduleProvider()

    @Provides
    fun provideCoroutineScope(): CoroutineScope? = null

    @Provides
    fun provideCoroutineContextProvider(): CoroutineContextProvider = CoroutineContextProvider()
}