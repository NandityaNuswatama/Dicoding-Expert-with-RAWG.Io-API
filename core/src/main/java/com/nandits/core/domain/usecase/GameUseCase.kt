package com.nandits.core.domain.usecase

import com.nandits.core.data.Resource
import com.nandits.core.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GameUseCase {
    fun getGameList(): Flow<Resource<List<Game>>>
    
    fun getDetailGame(id: Int): Flow<Game>
    
    fun getGameDetail(id: Int): Flow<Resource<Game>>
    
    fun getFavoriteGame(): Flow<List<Game>>
    
    fun setFavoriteGame(game: Game, state: Boolean)
}