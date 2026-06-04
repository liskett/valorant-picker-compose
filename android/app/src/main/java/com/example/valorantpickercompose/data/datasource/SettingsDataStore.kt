package com.example.valorantpickercompose.data.datasource

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.valorantpickercompose.domain.model.SettingType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private const val TAG = "SettingsDataStore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
//локальное хранение настроек, при перезапуске сохраняется.
class SettingsDataStore(
    context: Context
) {
    private val dataStore = context.dataStore
    private val SETTING_KEY = stringPreferencesKey("setting_type")
    private val STATS_VERSION_KEY = intPreferencesKey("stats_version")
    //поток, отдающий подписчикам текущую настройку
    val settingTypeFlow: Flow<SettingType> = dataStore.data
        .map { preferences ->
            val value = preferences[SETTING_KEY] ?: SettingType.BALANCED.name
            Log.d(TAG, "settingTypeFlow: read $value")
            SettingType.valueOf(value)
        }
        .catch { exception ->
            Log.e(TAG, "Error reading settingType", exception)
            emit(SettingType.BALANCED)
        }

    suspend fun saveSettingType(type: SettingType) {
        Log.d(TAG, "saveSettingType: $type")
        dataStore.edit { preferences ->
            preferences[SETTING_KEY] = type.name
        }
    }
    val statsVersionFlow: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[STATS_VERSION_KEY] ?: 0
        }
    suspend fun saveStatsVersion(version: Int) {
        dataStore.edit { preferences ->
            preferences[STATS_VERSION_KEY] = version
        }
    }
}