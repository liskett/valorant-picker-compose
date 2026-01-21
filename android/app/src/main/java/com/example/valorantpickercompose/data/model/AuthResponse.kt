package com.example.valorantpickercompose.data.model

data class AuthResponse(
    val message: String,
    val userId: Int? = null // nullable, чтобы безопасно обрабатывать null
)
