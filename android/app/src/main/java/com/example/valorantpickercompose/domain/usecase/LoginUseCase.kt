package com.example.valorantpickercompose.domain.usecase

import com.example.valorantpickercompose.domain.model.User
import com.example.valorantpickercompose.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    // suspend-функция для использования в корутинах (может приостанавливаться)
    // invoke - специальная функция, чтобы можно было вызывать объект как функцию,
    //т.е вместо loginUseCase.invoke(email,password) можно писать loginUseCase(email,password)
    suspend operator fun invoke(email: String, password: String): User {
        if (email.isBlank() || password.isBlank()) {  // можно в дальнейшем реализовать валидацию пароля и email - пока что просто проверка на пустоту
            throw IllegalArgumentException("Email and password must not be empty")
        }
        return withContext(Dispatchers.IO) { //переключаем выполнение на другой thread с помощью withContext с сетевым диспетчером IO
            authRepository.login(email, password) //запрос на сервер для входа в аккаунт
        }
    }
}
