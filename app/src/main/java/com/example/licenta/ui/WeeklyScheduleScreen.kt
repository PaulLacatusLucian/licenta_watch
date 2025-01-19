package com.example.licenta.ui
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import com.example.licenta.model.Schedule

@Composable
fun WeeklyScheduleScreen(schedule: List<Schedule>) {
    LazyColumn {
        items(schedule) { item ->
            Text(text = "${item.scheduleDay}: ${item.startTime} - ${item.endTime}")
            item.subjects.forEach {
                Text(text = it)
            }
        }
    }
}
