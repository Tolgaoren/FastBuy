package com.toren.producthub.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.toren.producthub.data.remote.dto.auth.AuthResponse
import com.toren.producthub.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : AuthRepository {

    companion object {
        private const val USER_KEY = "user_key"
    }

    override suspend fun saveUser(authResponse: AuthResponse) {
        val json = gson.toJson(authResponse)
        sharedPreferences.edit().putString(USER_KEY, json).apply()
    }

    override suspend fun getUser(): AuthResponse? {
        val json = sharedPreferences.getString(USER_KEY, null)
        return json?.let { gson.fromJson(it, AuthResponse::class.java) }
    }

    override suspend fun deleteUser() {
        sharedPreferences.edit().remove(USER_KEY).apply()
    }
}