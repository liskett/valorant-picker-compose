package com.example.valorantpickercompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.valorantpickercompose.data.local.entity.AgentStatsEntity
// описание операций с таблицей agent_stats в локальной Room-базе
// используется репозиторием для сохранения статистики, получения статистики по карте
// и проверки, заполнен ли локальный кэш.
@Dao
interface AgentStatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stats: List<AgentStatsEntity>)

    @Query("SELECT * FROM agent_stats WHERE mapName = :mapName")
    suspend fun getStatsForMap(mapName: String?): List<AgentStatsEntity>

    @Query("SELECT COUNT(*) FROM agent_stats")
    suspend fun getCount(): Int

    @Query("DELETE FROM agent_stats")
    suspend fun clearAll()
}