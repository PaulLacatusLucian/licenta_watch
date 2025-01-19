package com.example.licenta.network

import ScheduleApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://192.168.1.145:8080"

    val api: ScheduleApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // URL-ul bazei API
            .addConverterFactory(GsonConverterFactory.create()) // Convertor JSON -> Obiect Kotlin
            .build()
            .create(ScheduleApi::class.java) // Crearea implementării API
    }
}
