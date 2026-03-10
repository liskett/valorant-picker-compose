package com.example.valorantpickercompose.data.repository

import com.example.valorantpickercompose.data.api.AuthApi
import com.example.valorantpickercompose.data.model.LoginRequest
import com.example.valorantpickercompose.data.model.RegisterRequest
import com.example.valorantpickercompose.domain.model.User
import com.example.valorantpickercompose.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val api: AuthApi
) : AuthRepository {

    override suspend fun login(email: String, password: String): User {
        val request = LoginRequest(email, password)
        val response = api.login(request)
        return User(
            id = response.userId ?: -1,
            email = email
        )
    }

    override suspend fun register(email: String, password: String): User {
        val request = RegisterRequest(email, password)
        val response = api.register(request)
        return User(
            id = response.userId ?: -1,
            email = email
        )
    }
}
