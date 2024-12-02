package com.example.p1

data class User(
    val id: String?,
    val username: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String,
    val location: String? // Optional
)