package com.example.valorantpickercompose.data.model

//дата класс тела ответа сервера: успех/ошибка и айди
data class AuthResponse(
    val message: String,
    val userId: Int? = null
)
