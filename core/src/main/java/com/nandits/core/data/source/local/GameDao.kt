package com.nandits.core.data.source.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM gameDbx")
    fun getGameList(): Flow<List<com.nandits.core.data.source.local.GameEntity>>
    
    @Query("SELECT * FROM gameDbx WHERE gameId=:id")
    fun getGame(id: Int): Flow<com.nandits.core.data.source.local.GameEntity>
    
    @Query("SELECT * FROM gameDbx WHERE isFavorite = 1")
    fun getFavoriteGame(): Flow<List<com.nandits.core.data.source.local.GameEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: List<com.nandits.core.data.source.local.GameEntity>)
    
    @Update
    fun updateFavorite(gameEntity: com.nandits.core.data.source.local.GameEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateGameData(gameEntity: com.nandits.core.data.source.local.GameEntity)
}