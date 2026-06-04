package com.example.valorantpickercompose.data.repository

import com.example.valorantpickercompose.data.datasource.SettingsDataStore
import com.example.valorantpickercompose.domain.model.SettingType
import com.example.valorantpickercompose.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
// файл для передачи вызовов в datastore, т.е. прослойка между domain слоем и самим datastore
class SettingsRepositoryImpl(
    private val settingsDataStore: SettingsDataStore
) : SettingsRepository {
    override fun getSettingType(): Flow<SettingType> =
        settingsDataStore.settingTypeFlow
    override suspend fun saveSettingType(type: SettingType) =
        settingsDataStore.saveSettingType(type)
    override fun getStatsVersion(): Flow<Int> =
        settingsDataStore.statsVersionFlow
    override suspend fun saveStatsVersion(version: Int) =
        settingsDataStore.saveStatsVersion(version)
}