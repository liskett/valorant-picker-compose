package com.example.valorantpickercompose.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.valorantpickercompose.data.datasource.SettingsDataStore
import com.example.valorantpickercompose.data.repository.AuthRepositoryImpl
import com.example.valorantpickercompose.data.repository.RecommendationRepositoryImpl
import com.example.valorantpickercompose.data.repository.SettingsRepositoryImpl
import com.example.valorantpickercompose.data.repository.StatsRepositoryImpl
import com.example.valorantpickercompose.domain.repository.AuthRepository
import com.example.valorantpickercompose.domain.repository.RecommendationRepository
import com.example.valorantpickercompose.domain.repository.SettingsRepository
import com.example.valorantpickercompose.domain.repository.StatsRepository
import com.example.valorantpickercompose.domain.usecase.LoginUseCase
import com.example.valorantpickercompose.domain.usecase.RecommendAgentsUseCase
import com.example.valorantpickercompose.domain.usecase.RegisterUseCase
import com.example.valorantpickercompose.presentation.factory.AuthViewModelFactory
import com.example.valorantpickercompose.presentation.factory.PickerViewModelFactory
import com.example.valorantpickercompose.presentation.factory.SettingsViewModelFactory

// сбор зависимостей всего приложения в одном месте
object AppModule {
    // без Context
    private val authRepository: AuthRepository by lazy { AuthRepositoryImpl() }
    private val loginUseCase: LoginUseCase by lazy { LoginUseCase(authRepository) }
    private val registerUseCase: RegisterUseCase by lazy { RegisterUseCase(authRepository) }

    // с Context, нельзя создать сразу
    private lateinit var settingsRepository: SettingsRepository
    private lateinit var statsRepository: StatsRepository
    private lateinit var recommendationRepository: RecommendationRepository
    private lateinit var recommendAgentsUseCase: RecommendAgentsUseCase

    fun init(context: Context) {
        val appContext = context.applicationContext
        val dataStore = SettingsDataStore(appContext)

        settingsRepository = SettingsRepositoryImpl(dataStore)
        statsRepository = StatsRepositoryImpl(appContext, settingsRepository)
        recommendationRepository = RecommendationRepositoryImpl(statsRepository, settingsRepository)
        recommendAgentsUseCase = RecommendAgentsUseCase(recommendationRepository)
    }

    fun provideAuthViewModelFactory(): ViewModelProvider.Factory {
        return AuthViewModelFactory(loginUseCase, registerUseCase)
    }

    fun providePickerViewModelFactory(): ViewModelProvider.Factory {
        return PickerViewModelFactory(statsRepository, recommendAgentsUseCase)
    }

    fun provideSettingsViewModelFactory(): ViewModelProvider.Factory {
        return SettingsViewModelFactory(settingsRepository)
    }
}