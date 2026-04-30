package com.example.valorantpickercompose.data.network

import com.example.valorantpickercompose.data.api.AuthApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Retrofit-клиент для общения с сервером
object RetrofitClient {

    private const val BASE_URL = "http://10.0.2.2:8000/" //10.0.2.2 //192.168.0.105

    //логирование запросов
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY //логируем всё (заголовки, тело запроса и ответа)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging) //теперь каждый запрос будет логироваться в logcat
        .build()

    private val retrofit by lazy { // by lazy чтобы объект был создан только при первом обращении к retrofit
        Retrofit.Builder() //просто билдим ретрофит с заданными настройками выше
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    // создает объект с реализацией AuthApi, значит с помощью
    // него можно вызывать функции для передачи запросов на сервер
    }
}
