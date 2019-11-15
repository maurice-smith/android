package com.kingmo.example.teamroster.database

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Entity(tableName = "players")
data class Player(
    @ColumnInfo(name = "player_id") @PrimaryKey val playerId: Int,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "jersey_number") val jerseyNumber: String?,
    val position: String,
    @ColumnInfo(name = "photo_url") val photoUrl: String?,
    val bio: String?
)

@Dao
interface PlayerDao {
    @Query("SELECT * FROM players")
    fun getPlayers(): Flowable<List<Player>>

    @Query("SELECT * FROM players WHERE player_id = :playerId LIMIT 1")
    fun findPlayerById(playerId: Int): Flowable<Player>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg player: Player)

    @Update
    fun updatePlayers(vararg players: Player)

    @Delete
    fun delete(user: Player)
}