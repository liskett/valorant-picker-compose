package com.example.valorantpickercompose.data.model

import kotlinx.serialization.Serializable

// дата класс тела POST-запроса на вход в аккаунт. данные отправляются на сервер как JSON.
@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)
