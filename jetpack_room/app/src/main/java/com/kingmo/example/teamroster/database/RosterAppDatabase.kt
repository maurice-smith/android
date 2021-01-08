package com.kingmo.example.teamroster.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(PlayerModel::class), version = 1, exportSchema = true)
abstract class RosterAppDatabase: RoomDatabase() {
    abstract val playerDao: PlayerDao

    companion object {
        @Volatile
        private var INSTANCE: RosterAppDatabase? = null

        fun getInstance(context: Context): RosterAppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        RosterAppDatabase::class.java, "rosterDb").build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}

