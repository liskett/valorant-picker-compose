package com.example.valorantpickercompose


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class AgentState(
    val selectedAgents: List<String> = emptyList()
)


class AgentViewModel : ViewModel() {

    private val _state = MutableStateFlow(AgentState())
    val state: StateFlow<AgentState> = _state

    fun selectAgent(agent: String) {
        val current = _state.value.selectedAgents
        if (current.size < 4 && agent !in current) {
            _state.value = AgentState(current + agent)
        }
    }

    fun removeAgent(agent: String) {
        _state.value = AgentState(
            _state.value.selectedAgents - agent
        )
    }
}
