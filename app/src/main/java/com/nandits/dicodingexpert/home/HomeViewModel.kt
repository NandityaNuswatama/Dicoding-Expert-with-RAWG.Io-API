package com.nandits.dicodingexpert.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nandits.core.domain.usecase.GameUseCase

class HomeViewModel(private val useCase: GameUseCase): ViewModel() {
    val game = useCase.getGameList().asLiveData()
    fun getDetail(id: Int) = useCase.getGameDetail(id).asLiveData()
}