package com.example.valorantpickercompose.domain.repository

import com.example.valorantpickercompose.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): User
    suspend fun register(email: String, password: String): User
}
