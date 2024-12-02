package com.example.p1
import retrofit2.http.GET

interface ApiService {
    @GET("/services") // Chemin relatif à la base URL
    suspend fun getAllServices(): List<Service>
}
