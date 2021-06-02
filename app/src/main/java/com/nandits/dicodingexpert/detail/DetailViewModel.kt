package com.nandits.dicodingexpert.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nandits.core.domain.model.Game
import com.nandits.core.domain.usecase.GameUseCase

class DetailViewModel(private val useCase: GameUseCase): ViewModel() {
    fun setFavorite(game: Game, status: Boolean) = useCase.setFavoriteGame(game, status)
    fun getDetailGame(id: Int) = useCase.getDetailGame(id).asLiveData()
}