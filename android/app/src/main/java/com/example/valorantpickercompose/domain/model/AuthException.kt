package com.example.valorantpickercompose.domain.model

sealed class AuthException : Exception() {
    object InvalidPassword : AuthException()
    object UserNotFound : AuthException()
    object UserAlreadyExists : AuthException()
    object Unknown : AuthException()
}