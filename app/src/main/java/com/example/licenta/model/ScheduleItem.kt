package com.example.licenta.model

data class ScheduleItem(
    val id: Int,
    val scheduleDay: String,
    val startTime: String,
    val endTime: String,
    val subjects: List<String>,
    val teacher: Teacher
)
