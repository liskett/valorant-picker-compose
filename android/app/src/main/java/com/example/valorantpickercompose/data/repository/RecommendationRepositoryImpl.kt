package com.example.valorantpickercompose.data.repository

import com.example.valorantpickercompose.domain.model.Agent
import com.example.valorantpickercompose.domain.model.AgentCatalog
import com.example.valorantpickercompose.domain.model.AgentInfo
import com.example.valorantpickercompose.domain.model.AgentStats
import com.example.valorantpickercompose.domain.model.GameMap
import com.example.valorantpickercompose.domain.model.MapComposition
import com.example.valorantpickercompose.domain.model.Recommendation
import com.example.valorantpickercompose.domain.model.SettingType
import com.example.valorantpickercompose.domain.repository.RecommendationRepository
import com.example.valorantpickercompose.domain.repository.SettingsRepository
import com.example.valorantpickercompose.domain.repository.StatsRepository
import kotlinx.coroutines.flow.first

//реализация интерфейса из domain-слоя
class RecommendationRepositoryImpl(
    private val statsRepository: StatsRepository,
    private val settingsRepository: SettingsRepository
) : RecommendationRepository {
    // выдача рекомендации по заданным карте и агентам
    override suspend fun getRecommendation(
        mapName: GameMap?,
        selectedAgents: List<Agent>
    ): Recommendation {
        val settingType = settingsRepository.getSettingType().first() // учитывает приоритет рекомендаций из datastore
        val desiredRoles = MapComposition.desiredRolesForMap(mapName) // учитывает композицию на карте(пока что на всех одинаковая)
        // получений ролей уже выбранных агентов с помощью каталога
        val selectedRoles = selectedAgents.mapNotNull { agent ->
            AgentCatalog.allAgents.find { it.name == agent }?.role
        }
        //из нужной композиции удаляем роли уже выбранных агентов
        val missingRoles = desiredRoles.toMutableList().apply {
            selectedRoles.forEach { role -> remove(role) }
        }

        val mapStats = statsRepository.getStatsForMap(mapName)
        // для каждой недостающей роли выбираем топ3 агентов, которых еще не выбрали. сортируем по импакту
        val suggestedByRole = missingRoles.associateWith { role ->
            AgentCatalog.allAgents
                .filter { it.role == role && it.name !in selectedAgents }
                .sortedByDescending { agent ->
                    calculateImpactScore(agent, mapStats, settingType)
                }
                .take(3)
        }
        //лучший агент для отображения другой картинки в ui
        val topAgent = suggestedByRole
            .values
            .flatten()
            .maxByOrNull { agent ->
                calculateImpactScore(agent, mapStats, settingType)
            }

        return Recommendation(
            missingRoles = missingRoles,
            suggestedByRole = suggestedByRole,
            topAgent = topAgent
        )
    }
    // функция подсчета импакта в зависимости от статистики агентов
    // на выбранной карте и приоритета рекомендаций
    private fun calculateImpactScore(
        agent: AgentInfo,
        mapStats: Map<Agent, AgentStats>,
        settingType: SettingType
    ): Double {
        val stats = mapStats[agent.name]
        val wr = stats?.winRate ?: 0.0
        val pr = stats?.pickRate ?: 0.0
        return when (settingType) {
            SettingType.WIN_RATE_PRIORITY -> wr
            SettingType.PICK_RATE_PRIORITY -> pr
            SettingType.BALANCED -> wr * 0.7 + pr * 0.3
        }
    }
}