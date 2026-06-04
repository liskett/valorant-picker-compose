package com.example.valorantpickercompose.data.repository

import com.example.valorantpickercompose.data.model.LoginRequest
import com.example.valorantpickercompose.data.model.RegisterRequest
import com.example.valorantpickercompose.domain.model.User
import com.example.valorantpickercompose.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//реализация логики функций репозитория, имплементируется от интерфейса в domain-слое
class AuthRepositoryImpl : AuthRepository {

    override suspend fun login(email: String, password: String): User = withContext(Dispatchers.IO) {
        val request = LoginRequest(email, password) //тело запроса
        val response = com.example.valorantpickercompose.data.api.login(request) // отправляет запрос на сервер через вызов функции AuthApi и присваивает в эту переменную ответ сервера AuthResponse
        User(
            id = response.userId ?: -1,
            email = email
        )
    }

    override suspend fun register(email: String, password: String): User = withContext(Dispatchers.IO) {
        val request = RegisterRequest(email, password) //тело запроса
        val response = com.example.valorantpickercompose.data.api.register(request) // отправляет запрос на сервер через вызов функции AuthApi и присваивает в эту переменную ответ сервера AuthResponse
        User(
            id = response.userId ?: -1,
            email = email
        )
    }
}
