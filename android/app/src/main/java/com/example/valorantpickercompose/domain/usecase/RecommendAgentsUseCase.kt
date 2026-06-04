package com.example.valorantpickercompose.domain.usecase

import com.example.valorantpickercompose.domain.model.Agent
import com.example.valorantpickercompose.domain.model.GameMap
import com.example.valorantpickercompose.domain.model.Recommendation
import com.example.valorantpickercompose.domain.repository.RecommendationRepository

class RecommendAgentsUseCase(
    private val recommendationRepository: RecommendationRepository
) {
    suspend operator fun invoke(
        mapName: GameMap?,
        selectedAgents: List<Agent>
    ): Recommendation = recommendationRepository.getRecommendation(mapName, selectedAgents)
}