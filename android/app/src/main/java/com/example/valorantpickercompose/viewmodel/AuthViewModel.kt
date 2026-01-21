package com.example.valorantpickercompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valorantpickercompose.data.model.AuthResponse
import com.example.valorantpickercompose.data.repository.AuthRepository
import com.example.valorantpickercompose.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState { //состояния аутентификации
    object Idle : AuthState() // ничего не происходит(запроса пока не было)
    object Loading : AuthState()
    data class Success(val response: AuthResponse) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository(RetrofitClient.authApi)

    private val _loginState = MutableStateFlow<AuthState>(AuthState.Idle)
    val loginState: StateFlow<AuthState> = _loginState

    private val _registerState = MutableStateFlow<AuthState>(AuthState.Idle)
    val registerState: StateFlow<AuthState> = _registerState
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = AuthState.Loading
            try {
                val response = repository.login(email, password)
                _loginState.value = AuthState.Success(response)
            } catch (e: Exception) {
                _loginState.value = AuthState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
    fun register(email: String, password: String) {
        viewModelScope.launch {
            _registerState.value = AuthState.Loading
            try {
                val response = repository.register(email, password)
                _registerState.value = AuthState.Success(response)
            } catch (e: Exception) {
                _registerState.value = AuthState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
