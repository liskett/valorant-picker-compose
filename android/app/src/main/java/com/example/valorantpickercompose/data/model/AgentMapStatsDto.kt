package com.example.valorantpickercompose.data.model

import kotlinx.serialization.Serializable

// на вход дата классу приходят данные в строковом формате с процентами(например, "50,4%")
// dto класс преобразует их (убирает проценты и запятые и переводит их в тип Double в диапазоне от 0 до 1). было: 50,4% (строка), стало: 0.504(число)
//далее этот dto класс может передавать эти данные в другие слои
@Serializable
data class AgentMapStatsDto(
    val winRatePercent: String,
    val pickRatePercent: String
) {
    val winRate: Double
        get() = winRatePercent.removeSuffix("%").replace(',', '.').toDouble() / 100.0

    val pickRate: Double
        get() = pickRatePercent.removeSuffix("%").replace(',', '.').toDouble() / 100.0
}