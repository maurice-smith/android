package com.kingmo.example.teamroster.database

import androidx.room.*
import com.kingmo.example.teamroster.utils.EMPTY_STRING
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

@Entity(tableName = "players")
data class Player(
    // TODO: update & migrate @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "player_id") @PrimaryKey var playerId: Int = 0,
    @ColumnInfo(name = "first_name") var firstName: String = EMPTY_STRING,
    @ColumnInfo(name = "last_name") var lastName: String = EMPTY_STRING,
    @ColumnInfo(name = "jersey_number") var jerseyNumber: String? = EMPTY_STRING,
    var position: String = EMPTY_STRING,
    @ColumnInfo(name = "photo_url") var photoUrl: String? = EMPTY_STRING,
    var bio: String? = EMPTY_STRING
)

@Dao
interface PlayerDao {
    @Query("SELECT * FROM players")
    fun getPlayers(): Observable<List<Player>>

    @Query("SELECT * FROM players WHERE player_id = :playerId LIMIT 1")
    fun findPlayerById(playerId: Int): Flowable<Player>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg player: Player): Completable

    @Update
    fun updatePlayers(vararg players: Player)

    @Delete
    fun delete(user: Player)
}