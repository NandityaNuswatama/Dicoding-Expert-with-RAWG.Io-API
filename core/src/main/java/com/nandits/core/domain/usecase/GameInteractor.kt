package com.nandits.core.domain.usecase

import com.nandits.core.domain.model.Game
import com.nandits.core.domain.repository.IGameRepository
import kotlinx.coroutines.flow.Flow

class GameInteractor(private val gameRepository: IGameRepository): GameUseCase {
    override fun getGameList() = gameRepository.getGameList()
    
    override fun getDetailGame(id: Int): Flow<Game> = gameRepository.getDetailGame(id)
    
    override fun getGameDetail(id: Int) = gameRepository.getGameDetail(id)
    
    override fun getFavoriteGame(): Flow<List<Game>> = gameRepository.getFavoriteGame()
    
    override fun setFavoriteGame(game: Game, state: Boolean) = gameRepository.setFavoriteGame(game, state)
}