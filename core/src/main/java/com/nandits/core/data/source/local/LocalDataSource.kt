package com.nandits.core.data.source.local

import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val gameDao: com.nandits.core.data.source.local.GameDao) {
    fun getGameList(): Flow<List<com.nandits.core.data.source.local.GameEntity>> = gameDao.getGameList()
    
    fun getGame(id: Int): Flow<com.nandits.core.data.source.local.GameEntity> = gameDao.getGame(id)
    
    fun getFavoriteGame(): Flow<List<com.nandits.core.data.source.local.GameEntity>> = gameDao.getFavoriteGame()
    
    suspend fun insertGame(gameList: List<com.nandits.core.data.source.local.GameEntity>) = gameDao.insertGame(gameList)
    
    fun setFavoriteGame(gameEntity: com.nandits.core.data.source.local.GameEntity, state: Boolean){
        gameEntity.isFavorite = state
        gameDao.updateFavorite(gameEntity)
    }
    
    suspend fun updateDataGame(gameEntity: com.nandits.core.data.source.local.GameEntity) = gameDao.updateGameData(gameEntity)
}