package com.example.valorantpickercompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valorantpickercompose.domain.model.User
import com.example.valorantpickercompose.domain.usecase.LoginUseCase
import com.example.valorantpickercompose.domain.usecase.RegisterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


// класс хранящий виды состояний стейтов:
// ничего не происходит(Idle),
// запрос на сервер отправлен, ждем ответа(Loading),
// успех (Success)
// ошибка (Error)
sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}


// viewModel для хранения состояния loginState и registerState и для реализации функций login и register
class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    //скрытое свойство недоступное извне, обновляет состояния loginState через StateFlow
    private val _loginState = MutableStateFlow<AuthState>(AuthState.Idle)
    //к этому свойству можно обратиться извне(в MainActivity)
    val loginState: StateFlow<AuthState> = _loginState

    //скрытое свойство недоступное извне, обновляет состояния registerState через StateFlow
    private val _registerState = MutableStateFlow<AuthState>(AuthState.Idle)
    //к этому свойству можно обратиться извне(в MainActivity)
    val registerState: StateFlow<AuthState> = _registerState

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) { //запускаем корутину на thread с диспетчером IO с помощью launch
            _loginState.value = AuthState.Loading
            try {
                val user = loginUseCase(email, password) // отправляем запрос на сервер для входа таким путем: loginUseCase.invoke() -> authRepository.login() -> AuthRepositoryImpl().login() -> User()
                _loginState.value = AuthState.Success(user)
            } catch (e: Exception) {
                _loginState.value = AuthState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) { //запускаем корутину на thread с диспетчером IO с помощью launch
            _registerState.value = AuthState.Loading
            try {
                val user = registerUseCase(email, password) // отправляем запрос на сервер для регистрации таким путем: registerUseCase.invoke() -> authRepository.login() -> AuthRepositoryImpl().login() -> User()
                _registerState.value = AuthState.Success(user)
            } catch (e: Exception) {
                _registerState.value = AuthState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
