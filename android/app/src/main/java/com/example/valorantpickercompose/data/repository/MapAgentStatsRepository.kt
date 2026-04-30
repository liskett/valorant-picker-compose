package com.example.valorantpickercompose.data.repository

import android.content.Context
import com.example.valorantpickercompose.data.model.AgentMapStatsDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// просто показывает как представлены данные в json, используется для упрощения кода в raw
typealias MapAgentStatsRaw = Map<String, Map<String, List<String>>>

// класс для чтения данных из json и десериализации данных из строк в dto класс и кэширование результата
class MapAgentStatsRepository(private val context: Context) {

    private val gson = Gson()

    // кэш, чтобы не парсить JSON каждый раз
    private var cache: Map<String, Map<String, AgentMapStatsDto>>? = null

    // функция возврата десериализованного json файла, т.е. в result
    // хранятся данные mapName: {agentName: [wr,pr], ...}, ...
    fun getAll(): Map<String, Map<String, AgentMapStatsDto>> {
        cache?.let { return it } // если кэш уже есть, то не нужно парсить еще раз
        val json = context.assets
            .open("map_agent_stats.json")
            .bufferedReader()
            .use { it.readText() }

        val type = object : TypeToken<MapAgentStatsRaw>() {}.type
        val raw: MapAgentStatsRaw = gson.fromJson(json, type) //raw выглядит так mapOf(mapName to mapOf(agentName to listOf(wr,pr))) т.е
                                                                        // Ascent: {Clove: [50%, 10%]}, ...
        val result = raw.mapValues { (_, agentsMap) ->          //здесь мы берем конкретно вторую часть первой мапы и проходимся по ним,
                                                                // т.е Clove: [50%, 10%]. _ означает что ключ (первую часть мапы - название карты) не нужен
            agentsMap.mapValues { (_, list) ->          // дальше проходимся конкретно по второй части второй мапы выше, т.е. остается [50%,10%]
                AgentMapStatsDto(
                    winRatePercent = list.getOrNull(0) ?: "0%", // берем нулевой индекс в получившемся list - wr, если есть, иначе присваиваем 0%
                    pickRatePercent = list.getOrNull(1) ?: "0%" // берем первый индекс в получившемся list - pr, если есть, иначе присваиваем 0%
                )
            }
        }

        cache = result //теперь точно есть кэш
        return result
    }

    //функция для получения данных всех агентов по имени карты, т.е. по mapName получаем mapOf(agentName to [wr,pr])
    fun getForMap(mapName: String): Map<String, AgentMapStatsDto> {
        // "ASCENT" -> "Ascent", "BIND" -> "Bind" и т.п.
        val key = mapName.lowercase().replaceFirstChar { it.uppercase() }
        return getAll()[key] ?: emptyMap()
    }
}
