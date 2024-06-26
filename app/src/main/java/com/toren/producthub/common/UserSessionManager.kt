package com.toren.producthub.common

import com.toren.producthub.data.remote.dto.auth.AuthResponse

object UserSessionManager {
    private var authResponse: AuthResponse? = null

    fun isLogin(): Boolean {
        return authResponse != null
    }

    fun getCurrentUser(): AuthResponse? {
        return authResponse
    }

    fun loginUser(authResponse: AuthResponse) {
        this.authResponse = authResponse
    }

    fun logoutUser() {
        this.authResponse = null
    }
}