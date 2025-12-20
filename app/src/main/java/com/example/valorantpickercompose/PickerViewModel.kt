package com.example.valorantpickercompose

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale.getDefault

class PickerViewModel: ViewModel() {
    private val _state = MutableStateFlow(PickerState())
    val state: StateFlow<PickerState> = _state

    fun selectMap(map: String) {
        _state.value = _state.value.copy(selectedMap = map)
        println(state.value.selectedMap)
    }

    fun selectAgents(agents: List<String>) {
        _state.value = _state.value.copy(selectedAgents = agents)
        calculateResult()
        println(state.value.selectedMap)
    }

    private fun calculateResult() {
        val map = _state.value.selectedMap
        val agents = _state.value.selectedAgents
        val result = agents.map{ "$it ${map?.lowercase(getDefault())}" }
        _state.value = _state.value.copy(resultAgents = result)
    }
}