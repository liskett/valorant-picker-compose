package com.example.valorantpickercompose.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.valorantpickercompose.data.local.dao.AgentStatsDao
import com.example.valorantpickercompose.data.local.entity.AgentStatsEntity
// создание локальной бд, её описание.
@Database(
    entities = [AgentStatsEntity::class],
    version = 4,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun agentStatsDao(): AgentStatsDao

    companion object { //единственный экземлпяр
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase { //если бд уже есть - вернуть. если еще нет - создать через билдер
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "valorant_picker.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}