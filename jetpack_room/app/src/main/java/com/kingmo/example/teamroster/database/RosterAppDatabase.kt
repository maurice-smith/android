package com.kingmo.example.teamroster.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Player::class), version = 1, exportSchema = false)
abstract class RosterAppDatabase: RoomDatabase() {
    abstract fun getPlayerDao(): PlayerDao

    companion object {
        fun getInstance(context: Context): RosterAppDatabase {
            val database = Room.databaseBuilder(context.applicationContext,
                RosterAppDatabase::class.java, "roster.db").build()

            return database
        }
    }
}

