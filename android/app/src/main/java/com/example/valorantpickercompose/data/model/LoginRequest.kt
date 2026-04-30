package com.example.valorantpickercompose.data.model

// дата класс тела POST-запроса на вход в аккаунт
data class LoginRequest(
    val email: String,
    val password: String
)
