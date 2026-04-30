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

        val desiredRoles: List<Role> = MapComposition.desiredRolesForMap(mapName) //какие роли нужны для переданной карты

        val selectedRoles: List<Role> = selectedAgents
            .mapNotNull { AgentCatalog.byName[it]?.role }
        //получаем выбранные роли с помощью вызова свойства byName к каждому
        // агенту в переданном в конструкторе selectedAgents
        // и берем роль этого агента с помощью свойства role объекта дата класса AgentInfo

        val missingRoles = desiredRoles.toMutableList()
        selectedRoles.forEach { role ->
            missingRoles.remove(role)  // получаем список ролей, которые нам нужно выбрать просто вычитая все selectedRoles из desiredRoles
        }



        val mapStats = mapStatsRepository.getForMap(mapName) // получаем мапу agentName to [wr,pr] для выбранной карты


        val suggestedAgents: List<AgentInfo> = missingRoles // проходимся по всем невыбранным ролям
            .flatMap { role ->
                AgentCatalog.allAgents
                    .filter { it.role == role && it.name !in selectedAgents } // выбираем всех агентов с нужной ролью + кого еще нет в списке выбранных (по имени)
                    .sortedByDescending { agent -> //сортируем по убыванию "импакта" по такой логике: "импакт" = wr*0.7+pr*0.3, тк винрейт важнее (захардкодено, но можно будет как-то добавить ml-логику в будущем)
                        val key = agent.name
                        val stats = mapStats[key]
                        val wr = stats?.winRate ?: 0.0
                        val pr = stats?.pickRate ?: 0.0
                        wr * 0.7 + pr * 0.3
                    }
                    .take(3) //берем первых трех из каждой из ролей
            }

        return Recommendation(  // возвращаем рекомендацию
            missingRoles = missingRoles,
            suggestedAgents = suggestedAgents
        )
    }
}
