package com.example.valorantpickercompose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valorantpickercompose.R
import com.example.valorantpickercompose.domain.model.AuthException
import com.example.valorantpickercompose.domain.model.User
import com.example.valorantpickercompose.domain.usecase.LoginUseCase
import com.example.valorantpickercompose.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val messageRes: Int) : AuthState()
}

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<AuthState>(AuthState.Idle)
    val loginState: StateFlow<AuthState> = _loginState

    private val _registerState = MutableStateFlow<AuthState>(AuthState.Idle)
    val registerState: StateFlow<AuthState> = _registerState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = AuthState.Loading
            try {
                val user = loginUseCase(email, password)
                _loginState.value = AuthState.Success(user)
            } catch (e: Exception) {
                _loginState.value = AuthState.Error(
                    when (e) {
                        is AuthException.InvalidPassword -> R.string.error_invalid_password
                        is AuthException.UserNotFound -> R.string.error_user_not_found
                        is AuthException.UserAlreadyExists -> R.string.error_user_already_exists
                        else -> R.string.error_auth
                    }
                )
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _registerState.value = AuthState.Loading
            try {
                val user = registerUseCase(email, password)
                _registerState.value = AuthState.Success(user)
            } catch (e: Exception) {
                _registerState.value = AuthState.Error(
                    when (e) {
                        is AuthException.InvalidPassword -> R.string.error_invalid_password
                        is AuthException.UserNotFound -> R.string.error_user_not_found
                        is AuthException.UserAlreadyExists -> R.string.error_user_already_exists
                        else -> R.string.error_auth
                    }
                )
            }
        }
    }
    fun resetRegisterState() {
        _registerState.value = AuthState.Idle
    }
}