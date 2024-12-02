package com.example.p1

import retrofit2.Call
import retrofit2.http.GET

interface LocationApiService {

    // Endpoint to fetch all locations
    @GET("/location") // Make sure the URL matches the route in your backend
    fun getLocations(): Call<List<Location>>
}
