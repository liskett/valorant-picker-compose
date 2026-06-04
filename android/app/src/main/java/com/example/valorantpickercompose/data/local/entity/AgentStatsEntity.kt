package com.example.valorantpickercompose.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
// сущность, т.е. как выглядит таблица в бд, её столбцы, имя
@Entity(
    tableName = "agent_stats",
    indices = [Index(value = ["mapName", "agentName"], unique = true)] // уникальный, чтобы для одной пары карта/агент не было повторений(на всякий случай)
)
data class AgentStatsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val mapName: String,
    val agentName: String,
    val winRate: Double,
    val pickRate: Double
)