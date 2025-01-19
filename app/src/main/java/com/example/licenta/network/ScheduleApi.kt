import com.example.licenta.model.ScheduleItem
import retrofit2.http.GET
import retrofit2.http.Path

interface ScheduleApi {
    @GET("schedules/class/{className}/today")
    suspend fun getTodaySchedule(@Path("className") className: String): List<ScheduleItem>

    @GET("schedules/class/{className}/tomorrow")
    suspend fun getTomorrowSchedule(@Path("className") className: String): List<ScheduleItem>
}
