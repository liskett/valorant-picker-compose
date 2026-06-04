package com.example.valorantpickercompose.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.valorantpickercompose.domain.repository.StatsRepository
import com.example.valorantpickercompose.domain.usecase.RecommendAgentsUseCase
import com.example.valorantpickercompose.presentation.viewmodel.PickerViewModel

class PickerViewModelFactory(
    private val statsRepository: StatsRepository,
    private val recommendAgentsUseCase: RecommendAgentsUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PickerViewModel(
            statsRepository = statsRepository,
            recommendAgentsUseCase = recommendAgentsUseCase
        ) as T
    }
}