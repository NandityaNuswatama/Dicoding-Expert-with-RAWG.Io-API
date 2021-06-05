package com.nandits.core.data.source.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM gameDbx")
    fun getGameList(): Flow<List<GameEntity>>
    
    @Query("SELECT * FROM gameDbx WHERE gameId=:id")
    fun getGame(id: Int): Flow<GameEntity>
    
    @Query("SELECT * FROM gameDbx WHERE isFavorite = 1")
    fun getFavoriteGame(): Flow<List<GameEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: List<GameEntity>)
    
    @Update
    fun updateFavorite(gameEntity: GameEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateGameData(gameEntity: GameEntity)
}