package com.toren.producthub.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.producthub.common.Resource
import com.toren.producthub.common.UserSessionManager
import com.toren.producthub.data.remote.dto.auth.AuthResponse
import com.toren.producthub.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _getUserResult = MutableLiveData<AuthResponse?>()
    val getUserResult: LiveData<AuthResponse?> get() = _getUserResult

    private lateinit var userSessionManager: AuthResponse

    private val signOut = MutableLiveData<Resource<Boolean>>()
    val signOutResult: LiveData<Resource<Boolean>> get() = signOut

    init {
        UserSessionManager.getCurrentUser()?.let {
            userSessionManager = it
        }
        getUser()
    }

    private fun getUser() {
        try {
            val user = UserSessionManager.getCurrentUser()
            _getUserResult.value = user
        } catch (e: Exception) {
            Log.e("MainViewModel", "getUser: ${e.message}")
        }
    }

    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                UserSessionManager.logoutUser()
                authRepository.deleteUser()
                signOut.postValue(Resource.Success(true))
            } catch (e: Exception) {
                Log.e("MainViewModel", "signOut: ${e.message}")
            }
        }
    }
}