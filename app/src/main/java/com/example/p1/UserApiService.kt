package com.example.p1
import LoginRequest
import LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface UserApiService {

    @POST("/users/add")
    fun createUser(@Body user: User): Call<User>

    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>


    @GET("/location")
    fun getLocations(): Call<List<Location>>

    @GET("/")
    fun getUsers(): Call<List<User>>

    @GET("/{id}")
    fun getUserById(@Path("id") id: String): Call<User>

    @PUT("/{id}")
    fun updateUser(@Path("id") id: String, @Body user: User): Call<User>

    @DELETE("/{id}")
    fun deleteUser(@Path("id") id: String): Call<Void>
}
