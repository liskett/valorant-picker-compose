package com.example.valorantpickercompose.data.api

import com.example.valorantpickercompose.data.model.ApiErrorResponse
import com.example.valorantpickercompose.data.model.AuthResponse
import com.example.valorantpickercompose.data.model.LoginRequest
import com.example.valorantpickercompose.data.model.RegisterRequest
import com.example.valorantpickercompose.data.network.KtorClient
import com.example.valorantpickercompose.domain.model.AuthException
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

// отправляет POST-запросы login/register через общий KtorClient.
// KtorClient уже содержит BASE_URL, поэтому в post() указывается только endpoint.
// в тело запроса передается request, ответ преобразуется в AuthResponse.

private suspend fun handleAuthResponse(response: HttpResponse): AuthResponse {
    return if (response.status.isSuccess()) {
        response.body<AuthResponse>()
    } else {
        val error = response.body<ApiErrorResponse>()

        when (error.detail) {
            "Invalid password" -> throw AuthException.InvalidPassword
            "User not found" -> throw AuthException.UserNotFound
            "User already exists" -> throw AuthException.UserAlreadyExists
            else -> throw AuthException.Unknown
        }
    }
}

suspend fun login(request: LoginRequest): AuthResponse {
    val response = KtorClient.client.post("login") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }

    return handleAuthResponse(response)
}

suspend fun register(request: RegisterRequest): AuthResponse {
    val response = KtorClient.client.post("register") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }

    return handleAuthResponse(response)
}