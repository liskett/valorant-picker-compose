package com.example.valorantpickercompose.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.valorantpickercompose.domain.usecase.LoginUseCase
import com.example.valorantpickercompose.domain.usecase.RegisterUseCase
import com.example.valorantpickercompose.presentation.viewmodel.AuthViewModel

class AuthViewModelFactory(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(loginUseCase, registerUseCase) as T
    }
}