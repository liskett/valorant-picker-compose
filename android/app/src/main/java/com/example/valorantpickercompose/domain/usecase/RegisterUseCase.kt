package com.example.valorantpickercompose.domain.usecase

import com.example.valorantpickercompose.domain.model.User
import com.example.valorantpickercompose.domain.repository.AuthRepository
//файл аналогичен LoginUseCase, но для регистрации. надо потом валидацию сделать обязательно
class RegisterUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): User = authRepository.register(email, password)
}
