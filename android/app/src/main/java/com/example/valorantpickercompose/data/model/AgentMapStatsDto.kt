package com.example.valorantpickercompose.data.model

import kotlinx.serialization.Serializable

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


