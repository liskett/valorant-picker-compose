package com.example.valorantpickercompose.domain.repository

import com.example.valorantpickercompose.domain.model.SettingType
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSettingType(): Flow<SettingType>
    suspend fun saveSettingType(type: SettingType)
    fun getStatsVersion(): Flow<Int>
    suspend fun saveStatsVersion(version: Int)
}