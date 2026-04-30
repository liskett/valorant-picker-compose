package com.example.valorantpickercompose.data.model

// дата класс тела POST-запроса на создание аккаунта
data class RegisterRequest(
    val email: String,
    val password: String
)
