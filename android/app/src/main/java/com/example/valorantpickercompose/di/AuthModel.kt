package com.example.valorantpickercompose.di

import com.example.valorantpickercompose.data.api.AuthApi
import com.example.valorantpickercompose.data.network.RetrofitClient
import com.example.valorantpickercompose.data.repository.AuthRepositoryImpl
import com.example.valorantpickercompose.domain.repository.AuthRepository
import com.example.valorantpickercompose.domain.usecase.LoginUseCase
import com.example.valorantpickercompose.domain.usecase.RegisterUseCase
import com.example.valorantpickercompose.viewmodel.AuthViewModel

object AuthModule {

    private val authApi: AuthApi by lazy {
        RetrofitClient.authApi
    }

    private val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(authApi)
    }

    private val loginUseCase: LoginUseCase by lazy {
        LoginUseCase(authRepository)
    }

    private val registerUseCase: RegisterUseCase by lazy {
        RegisterUseCase(authRepository)
    }

    fun provideAuthViewModel(): AuthViewModel {
        return AuthViewModel(loginUseCase, registerUseCase)
    }
}
