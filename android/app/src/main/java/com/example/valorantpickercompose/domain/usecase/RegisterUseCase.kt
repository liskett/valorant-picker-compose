package com.example.valorantpickercompose.domain.usecase

import com.example.valorantpickercompose.domain.model.User
import com.example.valorantpickercompose.domain.repository.AuthRepository

class RegisterUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): User {
        // сюда можно добавить валидацию пароля
        return authRepository.register(email, password)
    }
}
