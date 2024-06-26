package com.toren.producthub.domain.repository

import com.toren.producthub.data.remote.dto.auth.AuthResponse

interface AuthRepository {

    suspend fun saveUser(authResponse: AuthResponse)

    suspend fun getUser(): AuthResponse?

    suspend fun deleteUser()
}