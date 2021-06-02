package com.nandits.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nandits.core.domain.usecase.GameUseCase

class FavoriteViewModel(private val useCase: GameUseCase): ViewModel() {
    val getFavorite = useCase.getFavoriteGame().asLiveData()
    fun getDetail(id: Int) = useCase.getGameDetail(id).asLiveData()
}