package com.example.valorantpickercompose.data.repository

import android.content.Context
import com.example.valorantpickercompose.data.model.AgentMapStatsDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

typealias MapAgentStatsRaw = Map<String, Map<String, List<String>>>

class MapAgentStatsRepository(private val context: Context) {

    private val gson = Gson()

    fun getAll(): Map<String, Map<String, AgentMapStatsDto>> {
        val json = context.assets
            .open("map_agent_stats.json")
            .bufferedReader()
            .use { it.readText() }

        val type = object : TypeToken<MapAgentStatsRaw>() {}.type
        val raw: MapAgentStatsRaw = gson.fromJson(json, type)

        return raw.mapValues { (_, agentsMap) ->
            agentsMap.mapValues { (_, list) ->
                AgentMapStatsDto(
                    winRatePercent = list.getOrNull(0) ?: "0%",
                    pickRatePercent = list.getOrNull(1) ?: "0%"
                )
            }
        }
    }

    fun getForMap(mapName: String): Map<String, AgentMapStatsDto> =
        getAll()[mapName] ?: emptyMap()
}



