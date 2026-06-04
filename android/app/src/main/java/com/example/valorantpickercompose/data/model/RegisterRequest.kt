package com.example.valorantpickercompose.data.model

import kotlinx.serialization.Serializable

// дата класс тела POST-запроса на создание аккаунта. данные отправляются на сервер как JSON.
@Serializable
data class RegisterRequest(
    val email: String,
    val password: String
)
