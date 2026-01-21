package com.example.valorantpickercompose.data.repository

import com.example.valorantpickercompose.data.api.AuthApi
import com.example.valorantpickercompose.data.model.AuthResponse
import com.example.valorantpickercompose.data.model.LoginRequest
import com.example.valorantpickercompose.data.model.RegisterRequest

class AuthRepository(private val api: AuthApi) {

    suspend fun login(email: String, password: String): AuthResponse {
        val request = LoginRequest(email, password)
        return api.login(request)
    }

    suspend fun register(email: String, password: String): AuthResponse {
        val request = RegisterRequest(email, password)
        return api.register(request)
    }
}
