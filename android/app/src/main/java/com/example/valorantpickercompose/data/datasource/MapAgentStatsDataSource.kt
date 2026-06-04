package com.example.valorantpickercompose.data.datasource

import android.content.Context
import com.example.valorantpickercompose.data.model.AgentMapStatsDto
import com.google.gson.Gson
// тип для упрощения кода
typealias MapAgentStatsRaw = Map<String, Map<String, List<String>>>

data class MapAgentStatsJson(
    val version: Int,
    val maps: MapAgentStatsRaw
)
//чтение статистики из json
class MapAgentStatsDataSource(private val context: Context) {

    private val gson = Gson()
    //кэш, чтобы не читать данные каждый раз заново
    private var cache: Pair<Int, Map<String, Map<String, AgentMapStatsDto>>>? = null

    fun getAll(): Pair<Int, Map<String, Map<String, AgentMapStatsDto>>> {
        cache?.let { return it }

        val json = context.assets
            .open("map_agent_stats.json")
            .bufferedReader()
            .use { it.readText() }

        val type = object : com.google.gson.reflect.TypeToken<MapAgentStatsJson>() {}.type
        val parsed: MapAgentStatsJson = gson.fromJson(json, type)
        //преобразование строки в dto для дальнейшей работы
        val result = parsed.maps.mapValues { (_, agentsMap) ->
            agentsMap.mapValues { (_, list) ->
                AgentMapStatsDto(
                    winRatePercent = list.getOrNull(0) ?: "0%",
                    pickRatePercent = list.getOrNull(1) ?: "0%"
                )
            }
        }
        val finalResult = parsed.version to result
        cache = finalResult
        return finalResult
    }
}