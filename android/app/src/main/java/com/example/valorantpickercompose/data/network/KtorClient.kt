package com.example.valorantpickercompose.data.network

import io.ktor.client.plugins.defaultRequest
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

// клиент для отправки запросов с помощью http на сервер.
object KtorClient {
    private const val BASE_URL = "http://10.0.2.2:8000/" //10.0.2.2 //192.168.0.105

    val client = HttpClient(Android) {

        expectSuccess = false

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true // нестрогий парсинг
            })
        }

        install(Logging) {
            level = LogLevel.ALL // логирование всего запроса
        }


        defaultRequest {
            url(BASE_URL)  // базовый url, к нему добавляются end-pointы в AuthService
        }


    }
}