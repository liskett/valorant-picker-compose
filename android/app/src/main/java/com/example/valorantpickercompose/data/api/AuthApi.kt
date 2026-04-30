package com.example.valorantpickercompose.data.api

import com.example.valorantpickercompose.data.model.AuthResponse
import com.example.valorantpickercompose.data.model.LoginRequest
import com.example.valorantpickercompose.data.model.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

// интерфейс для Retrofit для обработки запросов на сервер
interface AuthApi {

    // при вызове функции login() создается POST-запрос
    // с телом - объектом дата класса LoginRequest и возвращает AuthResponse
    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): AuthResponse

    // при вызове функции register() создается POST-запрос
    // с телом - объектом дата класса RegisterRequest и возвращает AuthResponse
    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): AuthResponse
}
