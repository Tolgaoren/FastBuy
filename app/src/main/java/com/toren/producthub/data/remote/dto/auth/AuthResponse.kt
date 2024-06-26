package com.toren.producthub.data.remote.dto.auth

data class AuthResponse(
    val email: String,
    val firstName: String,
    val gender: String,
    val id: Int,
    val image: String,
    val lastName: String,
    val refreshToken: String,
    val token: String,
    val username: String
)