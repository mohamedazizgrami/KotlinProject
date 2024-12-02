import com.example.p1.LocationApiService
import com.example.p1.Provider
import com.example.p1.User
import com.example.p1.UserApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://192.168.1.14:3031/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Create a property for UserApiService
    val userApiService: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }
    val locationApiService: LocationApiService by lazy {
        retrofit.create(LocationApiService::class.java)
    }
}
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val id: String,
    val username: String,
    val role: String, // Should be "User" or "Provider"
    val token: String, // JWT Token
    val data: Any? // Either User or Provider data depending on the role
)




