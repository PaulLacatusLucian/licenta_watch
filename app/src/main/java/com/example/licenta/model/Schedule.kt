package com.example.licenta.model

data class Schedule(
    val id: Long,
    val scheduleDay: String,
    val startTime: String,
    val endTime: String,
    val subjects: List<String>
)
