package com.example.valorantpickercompose.domain.repository

import com.example.valorantpickercompose.domain.model.User

//интерфейс, который показывает, какие есть функции для регистрации и входа, т.е. просто показывает правила, но без реализации логики
interface AuthRepository {
    suspend fun login(email: String, password: String): User
    suspend fun register(email: String, password: String): User
}
