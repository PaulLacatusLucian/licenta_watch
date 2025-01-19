import com.example.licenta.model.ScheduleItem
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ScheduleApi {
    @GET("schedules/class/{classId}/today")
    suspend fun getSchedule(@Path("classId") classId: Long): List<ScheduleItem>
}


