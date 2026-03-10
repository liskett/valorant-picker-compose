package com.example.valorantpickercompose.domain.usecase

import com.example.valorantpickercompose.data.repository.MapAgentStatsRepository
import com.example.valorantpickercompose.domain.model.AgentCatalog
import com.example.valorantpickercompose.domain.model.AgentInfo
import com.example.valorantpickercompose.domain.model.MapComposition
import com.example.valorantpickercompose.domain.model.Recommendation
import com.example.valorantpickercompose.domain.model.Role

class RecommendAgentsUseCase(
    private val mapStatsRepository: MapAgentStatsRepository
) {

    fun getRecommendationForMap(
        mapName: String,
        selectedAgents: List<String>
    ): Recommendation {

        val desiredRoles: List<Role> = MapComposition.desiredRolesForMap(mapName)

        val selectedRoles: List<Role> = selectedAgents
            .mapNotNull { AgentCatalog.byName[it]?.role }

        val missingRoles: List<Role> = desiredRoles
            .groupBy { it }
            .flatMap { (role, desiredList) ->
                val needCount = desiredList.size
                val haveCount = selectedRoles.count { it == role }
                val missingCount = (needCount - haveCount).coerceAtLeast(0)
                List(missingCount) { role }
            }

        if (missingRoles.isEmpty()) {
            return Recommendation(
                missingRoles = emptyList(),
                suggestedAgents = emptyList()
            )
        }

        val mapStats = mapStatsRepository.getForMap(mapName)

        val suggestedAgents: List<AgentInfo> = missingRoles
            .flatMap { role ->
                AgentCatalog.allAgents
                    .filter { it.role == role && it.name !in selectedAgents }
                    .sortedByDescending { agent ->
                        val s = mapStats[agent.name]
                        val wr = s?.winRate ?: agent.winRate
                        val pr = s?.pickRate ?: agent.pickRate
                        wr * 0.7 + pr * 0.3
                    }
                    .take(2)
            }
            .distinctBy { it.name }

        return Recommendation(
            missingRoles = missingRoles,
            suggestedAgents = suggestedAgents
        )
    }
}
