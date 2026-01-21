package com.example.valorantpickercompose.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class PickerState(
    val selectedMap: String? = null,
    val selectedAgents: List<String> = emptyList(),
    val resultAgents: List<String> = emptyList()
)
class PickerViewModel: ViewModel() {
    private val _state = MutableStateFlow(PickerState())
    val state: StateFlow<PickerState> = _state

    fun selectMap(map: String) {
        _state.value = _state.value.copy(selectedMap = map)
    }

}