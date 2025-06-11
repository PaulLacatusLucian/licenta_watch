package com.example.licenta.api

import com.example.licenta.model.ScheduleItem
import retrofit2.http.GET
import retrofit2.http.Path

interface ScheduleApi {
    @GET("schedules/public/class/{className}/today")
    suspend fun getTodaySchedule(@Path("className") className: String): List<ScheduleItem>

    @GET("schedules/public/class/{className}/tomorrow")
    suspend fun getTomorrowSchedule(@Path("className") className: String): List<ScheduleItem>

    @GET("schedules/public/class/{className}/day/{dayName}")
    suspend fun getScheduleForDay(
        @Path("className") className: String,
        @Path("dayName") dayName: String
    ): List<ScheduleItem>
}