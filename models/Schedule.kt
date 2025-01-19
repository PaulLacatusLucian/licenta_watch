package com.example.app.model

data class Schedule(
    val id: Long,
    val scheduleDay: String,
    val startTime: String,
    val endTime: String,
    val subjects: List<String>
)