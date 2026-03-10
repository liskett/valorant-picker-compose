package com.example.valorantpickercompose.domain.usecase

import com.example.valorantpickercompose.domain.model.User
import com.example.valorantpickercompose.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): User {
        if (email.isBlank() || password.isBlank()) {
            throw IllegalArgumentException("Email and password must not be empty")
        }
        return authRepository.login(email, password)
    }
}
