package com.nandits.dicodingexpert.di

import com.nandits.core.domain.usecase.GameInteractor
import com.nandits.core.domain.usecase.GameUseCase
import com.nandits.dicodingexpert.detail.DetailViewModel
import com.nandits.dicodingexpert.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<GameUseCase> { GameInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}