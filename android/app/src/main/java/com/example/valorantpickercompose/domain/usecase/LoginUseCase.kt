package com.example.valorantpickercompose.domain.usecase

import com.example.valorantpickercompose.domain.model.User
import com.example.valorantpickercompose.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    // suspend-функция для использования в корутинах (может приостанавливаться)
    // invoke - специальная функция, чтобы можно было вызывать объект как функцию,
    //т.е вместо loginUseCase.invoke(email,password) можно писать loginUseCase(email,password)
    suspend operator fun invoke(
        email: String,
        password: String
    ): User = authRepository.login(email, password) //запрос на сервер для входа в аккаунт
}

