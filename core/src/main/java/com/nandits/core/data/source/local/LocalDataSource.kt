package com.nandits.core.data.source.local

import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val gameDao: GameDao) {
    fun getGameList(): Flow<List<GameEntity>> = gameDao.getGameList()
    
    fun getGame(id: Int): Flow<GameEntity> = gameDao.getGame(id)
    
    fun getFavoriteGame(): Flow<List<GameEntity>> = gameDao.getFavoriteGame()
    
    suspend fun insertGame(gameList: List<GameEntity>) = gameDao.insertGame(gameList)
    
    fun setFavoriteGame(gameEntity: GameEntity, state: Boolean){
        gameEntity.isFavorite = state
        gameDao.updateFavorite(gameEntity)
    }
    
    suspend fun updateDataGame(gameEntity: GameEntity) = gameDao.updateGameData(gameEntity)
}