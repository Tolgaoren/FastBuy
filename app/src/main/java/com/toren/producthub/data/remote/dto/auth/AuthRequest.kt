package com.toren.producthub.data.remote.dto.auth

data class AuthRequest(
    val username: String,
    val password: String,
    val expiresInMins: Int = 60
)
