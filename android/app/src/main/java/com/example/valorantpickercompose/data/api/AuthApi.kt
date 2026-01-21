package com.example.valorantpickercompose.data.api

import com.example.valorantpickercompose.data.model.AuthResponse
import com.example.valorantpickercompose.data.model.LoginRequest
import com.example.valorantpickercompose.data.model.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): AuthResponse

    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): AuthResponse
}
