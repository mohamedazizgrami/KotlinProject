package com.example.p1

data class Provider(
    val id: String, // MongoDB's ObjectId
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phone: String,
    val location: String? // Nullable if location is optional
)
