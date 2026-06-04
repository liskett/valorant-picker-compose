package com.example.valorantpickercompose.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valorantpickercompose.domain.model.SettingType
import com.example.valorantpickercompose.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "SettingsViewModel"

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _settingType = MutableStateFlow(SettingType.BALANCED)
    val settingType: StateFlow<SettingType> = _settingType.asStateFlow()

    init {
        Log.d(TAG, "ViewModel init")
        viewModelScope.launch {
            settingsRepository.getSettingType().collect { type ->
                Log.d(TAG, "Setting type collected: $type")
                _settingType.value = type
            }
        }
    }
    fun saveSetting(type: SettingType) {
        viewModelScope.launch {
            Log.d(TAG, "saveSetting: $type")
            settingsRepository.saveSettingType(type)
        }
    }
}