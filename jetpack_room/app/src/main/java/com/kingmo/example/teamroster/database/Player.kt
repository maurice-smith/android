package com.kingmo.example.teamroster.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "players")
data class Player(
    @ColumnInfo(name = "player_id") @PrimaryKey val playerId: Int,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "jersey_number") val jerseyNumber: Int?,
    val position: String?,
    @ColumnInfo(name = "photo_url") val photoUrl: String?,
    val bio: String?
)

@Dao
interface PlayerDao {
    @Query("SELECT * FROM players")
    fun getPlayers(): LiveData<List<Player>>

    @Query("SELECT * FROM players WHERE player_id = :playerId LIMIT 1")
    fun findPlayerById(playerId: Int): LiveData<Player>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg player: Player)

    @Update
    fun updatePlayers(vararg players: Player)

    @Delete
    fun delete(user: Player)
}