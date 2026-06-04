package com.example.valorantpickercompose.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valorantpickercompose.domain.model.Agent
import com.example.valorantpickercompose.domain.model.AgentStats
import com.example.valorantpickercompose.domain.model.GameMap
import com.example.valorantpickercompose.domain.model.Recommendation
import com.example.valorantpickercompose.domain.repository.StatsRepository
import com.example.valorantpickercompose.domain.usecase.RecommendAgentsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PickerState(
    val selectedMap: GameMap? = null,
    val selectedAgents: List<Agent> = emptyList()
)

sealed class ResultUiState {
    object Idle : ResultUiState()
    object Loading : ResultUiState()
    data class Success(
        val mapStats: Map<Agent, AgentStats>,
        val recommendation: Recommendation
    ) : ResultUiState()
    data class Error(val message: String) : ResultUiState()
}

class PickerViewModel(
    private val statsRepository: StatsRepository,
    private val recommendAgentsUseCase: RecommendAgentsUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            try {
                if (statsRepository.shouldRefreshCache()) {
                    statsRepository.refreshCache()
                }
            } catch (e: Exception) {
                Log.e("PickerViewModel", "Failed to refresh cache", e)
            }
        }
    }

    private val _state = MutableStateFlow(PickerState())
    val state: StateFlow<PickerState> = _state.asStateFlow()

    private val _uiState = MutableStateFlow<ResultUiState>(ResultUiState.Idle)
    val uiState: StateFlow<ResultUiState> = _uiState.asStateFlow()

    fun selectMap(map: GameMap) {
        _state.value = _state.value.copy(selectedMap = map)
    }

    fun selectAgent(agent: Agent) {
        val current = _state.value.selectedAgents
        if (current.size < 4 && agent !in current) {
            _state.value = _state.value.copy(selectedAgents = current + agent)
        }
    }

    fun removeAgent(agent: Agent) {
        _state.value = _state.value.copy(
            selectedAgents = _state.value.selectedAgents - agent
        )
    }

    fun loadResult(selectedMap: GameMap?, selectedAgents: List<Agent>) {
        viewModelScope.launch {
            _uiState.value = ResultUiState.Loading
            try {
                val mapStats = statsRepository.getStatsForMap(selectedMap)
                val recommendation = recommendAgentsUseCase(
                    mapName = selectedMap,
                    selectedAgents = selectedAgents
                )
                _uiState.value = ResultUiState.Success(mapStats, recommendation)
            } catch (e: Exception) {
                _uiState.value = ResultUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}