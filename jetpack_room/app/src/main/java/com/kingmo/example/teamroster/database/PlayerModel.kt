package com.kingmo.example.teamroster.database

import androidx.room.*
import com.kingmo.example.teamroster.utils.EMPTY_STRING
import io.reactivex.Completable
import io.reactivex.Observable

//https://via.placeholder.com/150.png/FF0000/000000?text=Profile+Image

@Entity(tableName = "players")
data class PlayerModel(
    @ColumnInfo(name = "player_id") @PrimaryKey(autoGenerate = true) var playerId: Int = 0,
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
    fun getPlayers(): Observable<List<PlayerModel>>

    @Query("SELECT * FROM players WHERE player_id = :playerId LIMIT 1")
    fun findPlayerById(playerId: Int): Observable<PlayerModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg playerModel: PlayerModel): Completable

    @Update
    fun updatePlayers(vararg playerModels: PlayerModel)

    @Delete
    fun delete(user: PlayerModel): Completable
}