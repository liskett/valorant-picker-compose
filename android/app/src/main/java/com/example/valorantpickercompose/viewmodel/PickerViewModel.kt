package com.example.valorantpickercompose.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// дата класс для хранения состояния выбора карты и агентов
data class PickerState(
    val selectedMap: String? = null,
    val selectedAgents: List<String> = emptyList()
)

class PickerViewModel : ViewModel() {
    private val _state = MutableStateFlow(PickerState())
    val state: StateFlow<PickerState> = _state

    //обновление состояния при выборе карты
    fun selectMap(map: String) {
        _state.value = _state.value.copy(selectedMap = map)
    }
    //обновление состояния при выборе агента
    fun selectAgent(agent: String) {
        val current = _state.value.selectedAgents
        if (current.size < 4 && agent !in current) {
            _state.value = _state.value.copy(selectedAgents = current + agent)
        }
    }
    //обновление состояния при удалении агента
    fun removeAgent(agent: String) {
        _state.value = _state.value.copy(selectedAgents = _state.value.selectedAgents - agent)
    }
}
