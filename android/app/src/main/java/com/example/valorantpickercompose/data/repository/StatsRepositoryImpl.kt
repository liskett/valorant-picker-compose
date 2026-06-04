package com.example.valorantpickercompose.data.repository

import android.content.Context
import com.example.valorantpickercompose.data.datasource.MapAgentStatsDataSource
import com.example.valorantpickercompose.data.local.AppDatabase
import com.example.valorantpickercompose.data.local.entity.AgentStatsEntity
import com.example.valorantpickercompose.domain.model.Agent
import com.example.valorantpickercompose.domain.model.AgentStats
import com.example.valorantpickercompose.domain.model.GameMap
import com.example.valorantpickercompose.domain.repository.SettingsRepository
import com.example.valorantpickercompose.domain.repository.StatsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

//преобразование имени из бд в объект enum класса
private fun mapAgentNameFromDb(nameFromDb: String): Agent {
    return when (nameFromDb) {
        "KAY/O" -> Agent.KAYO
        else -> Agent.valueOf(nameFromDb.uppercase())
    }
}
//реализация интерфейса из domain слоя. отвечает за статистику агентов по конкретной карте
// читает статистику из json -> записывает в room-бд -> позже берет статистику уже из room.
class StatsRepositoryImpl(
    context: Context,
    private val settingsRepository: SettingsRepository
) : StatsRepository {
    private val database = AppDatabase.getDatabase(context)
    private val dao = database.agentStatsDao()
    private val jsonRepo = MapAgentStatsDataSource(context)

    // проверяет, есть ли уже данные в таблице agent_stats
    // используется во viewModel при запуске и
    // не парсит json каждый раз при запуске приложения
    override suspend fun isCacheAvailable(): Boolean = withContext(Dispatchers.IO) {
        dao.getCount() > 0
    }

    //обновление бд
    override suspend fun refreshCache() = withContext(Dispatchers.IO) {
        val (version, allStats) = jsonRepo.getAll()

        val entities = mutableListOf<AgentStatsEntity>()

        allStats.forEach { (mapName, agentsMap) ->
            agentsMap.forEach { (agentName, statsDto) ->
                entities.add(
                    AgentStatsEntity(
                        mapName = mapName,
                        agentName = agentName,
                        winRate = statsDto.winRate,
                        pickRate = statsDto.pickRate
                    )
                )
            }
        }

        dao.clearAll()
        dao.insertAll(entities)

        settingsRepository.saveStatsVersion(version)
    }

    // получение данных всех агентов для конкретной карты из бд и преобразование entity в AgentStats
    override suspend fun getStatsForMap(mapName: GameMap?): Map<Agent, AgentStats> =
        withContext(Dispatchers.IO) {
            val stats = dao.getStatsForMap(mapName?.name?.lowercase()?.replaceFirstChar { it.uppercase() })
            stats.associate { entity ->
                val agent = mapAgentNameFromDb(entity.agentName)
                agent to AgentStats(
                    winRate = entity.winRate,
                    pickRate = entity.pickRate
                )
            }
        }
    override suspend fun shouldRefreshCache(): Boolean = withContext(Dispatchers.IO) {
        val (jsonVersion, _) = jsonRepo.getAll()
        val savedVersion = settingsRepository.getStatsVersion().first()

        dao.getCount() == 0 || savedVersion != jsonVersion
    }
}