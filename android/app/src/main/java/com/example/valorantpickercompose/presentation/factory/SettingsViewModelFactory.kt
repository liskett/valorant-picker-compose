package com.example.valorantpickercompose.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.valorantpickercompose.domain.repository.SettingsRepository
import com.example.valorantpickercompose.presentation.viewmodel.SettingsViewModel

class SettingsViewModelFactory(
    private val settingsRepository: SettingsRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(settingsRepository) as T
    }
}