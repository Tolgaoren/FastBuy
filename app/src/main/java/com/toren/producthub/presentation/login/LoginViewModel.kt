package com.toren.producthub.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.producthub.common.UserSessionManager
import com.toren.producthub.data.remote.dto.auth.AuthRequest
import com.toren.producthub.domain.repository.AuthRepository
import com.toren.producthub.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: ProductRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<Unit>>()
    val loginResult: LiveData<Result<Unit>> get() = _loginResult

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    init {
        loginCheck()
    }

    private fun loginCheck() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = authRepository.getUser()
            if (user != null) {
                UserSessionManager.loginUser(user)
                _loginResult.postValue(Result.success(Unit))
            } else {
                _loading.postValue(false)
            }
        }
    }

    fun login(request: AuthRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            try {
                val response = loginRepository.login(request)
                val saveUser = authRepository.saveUser(response)
                UserSessionManager.loginUser(response)
                _loginResult.postValue(Result.success(saveUser))
            } catch (e: Exception) {
                _loginResult.postValue(Result.failure(e))
            }
            _loading.postValue(false)
        }
    }
}