package com.example.valorantpickercompose.domain.model

data class AgentInfo(
    val name: String,
    val role: Role,
    val winRate: Double,  // 0.56 = 56%
    val pickRate: Double  // 0.20 = 20%
)
