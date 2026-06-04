package com.example.valorantpickercompose.data.model

import kotlinx.serialization.Serializable

//дата класс тела ответа сервера: успех/ошибка и айди
@Serializable
data class AuthResponse(
    val message: String,
    val userId: Int? = null
)
