package com.example.valorantpickercompose.domain.repository

import com.example.valorantpickercompose.domain.model.Agent
import com.example.valorantpickercompose.domain.model.AgentStats
import com.example.valorantpickercompose.domain.model.GameMap

interface StatsRepository {
    suspend fun getStatsForMap(mapName: GameMap?): Map<Agent, AgentStats>
    suspend fun isCacheAvailable(): Boolean
    suspend fun refreshCache()
    suspend fun shouldRefreshCache(): Boolean
}