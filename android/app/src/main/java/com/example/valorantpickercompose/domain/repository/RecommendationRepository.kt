package com.example.valorantpickercompose.domain.repository

import com.example.valorantpickercompose.domain.model.Agent
import com.example.valorantpickercompose.domain.model.GameMap
import com.example.valorantpickercompose.domain.model.Recommendation

interface RecommendationRepository {
    suspend fun getRecommendation(
        mapName: GameMap?,
        selectedAgents: List<Agent>
    ): Recommendation
}