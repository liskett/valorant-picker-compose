package com.example.valorantpickercompose.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorResponse(
    val detail: String
)