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
                val response = RetrofitInstance.api.getTodaySchedule(className)
                _schedule.value = response // Stocăm răspunsul complet
            } catch (e: Exception) {
                println("Eroare: ${e.message}")
                _schedule.value = emptyList() // Returnăm o listă goală în caz de eroare
            }
        }
    }

    fun fetchSchedulesForTomorrow(className: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getTomorrowSchedule(className)
                _schedule.value = response // Stocăm răspunsul complet
            } catch (e: Exception) {
                println("Eroare: ${e.message}")
                _schedule.value = emptyList() // Returnăm o listă goală în caz de eroare
            }
        }
    }
}
