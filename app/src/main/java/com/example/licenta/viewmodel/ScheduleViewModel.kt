package com.example.licenta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.licenta.model.ScheduleItem
import com.example.licenta.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ScheduleViewModel : ViewModel() {
    private val _schedule = MutableStateFlow<List<ScheduleItem>?>(null)
    val schedule: StateFlow<List<ScheduleItem>?> get() = _schedule

    fun fetchSchedulesForToday(className: String) {
        viewModelScope.launch {
            try {
                println("Fetching today's schedule for class: $className")
                val response = RetrofitInstance.api.getTodaySchedule(className)
                println("Received ${response.size} items for today")
                _schedule.value = response
            } catch (e: Exception) {
                println("Eroare fetchSchedulesForToday: ${e.message}")
                e.printStackTrace()
                _schedule.value = emptyList()
            }
        }
    }

    fun fetchSchedulesForTomorrow(className: String) {
        viewModelScope.launch {
            try {
                println("Fetching tomorrow's schedule for class: $className")
                val response = RetrofitInstance.api.getTomorrowSchedule(className)
                println("Received ${response.size} items for tomorrow")
                _schedule.value = response
            } catch (e: Exception) {
                println("Eroare fetchSchedulesForTomorrow: ${e.message}")
                e.printStackTrace()
                _schedule.value = emptyList()
            }
        }
    }

    fun fetchSchedulesForDay(className: String, dayName: String) {
        viewModelScope.launch {
            try {
                println("Fetching schedule for class: $className, day: $dayName")
                val response = RetrofitInstance.api.getScheduleForDay(className, dayName)
                println("Received ${response.size} items for $dayName")
                _schedule.value = response
            } catch (e: Exception) {
                println("Eroare fetchSchedulesForDay: ${e.message}")
                e.printStackTrace()
                _schedule.value = emptyList()
            }
        }
    }


    fun clearSchedule() {
        _schedule.value = null
    }
}