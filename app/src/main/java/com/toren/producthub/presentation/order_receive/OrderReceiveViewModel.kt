package com.toren.producthub.presentation.order_receive

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.producthub.common.Resource
import com.toren.producthub.common.UserSessionManager
import com.toren.producthub.data.remote.dto.auth.AuthResponse
import com.toren.producthub.data.remote.dto.cart.AddCartRequest
import com.toren.producthub.data.remote.dto.cart.AddCartResponse
import com.toren.producthub.data.remote.dto.user.toUser
import com.toren.producthub.domain.model.AddCart
import com.toren.producthub.domain.model.User
import com.toren.producthub.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class OrderReceiveViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel() {

    private lateinit var userSessionManager: AuthResponse

    private val _addCart = MutableLiveData<AddCartResponse>()
    val addCart: LiveData<AddCartResponse> = _addCart

    private val _user = MutableLiveData<Resource<User>>()
    val user: LiveData<Resource<User>> = _user

    init {
        UserSessionManager.getCurrentUser()?.let {
            userSessionManager = it
        }
        getUserInfo()
    }

    fun addCart(cart: AddCart) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val addCartRequest = AddCartRequest(
                    userId = userSessionManager.id,
                    products = listOf(cart)
                )
                val request = productRepository.addCart(addCartRequest)
                _addCart.postValue(request)
            } catch (e: Exception) {
                Log.e("LikeRepository", e.localizedMessage ?: "An unexpected error occurred")
            }
        }
    }

    private fun getUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = productRepository.getUser(userSessionManager.id)
                _user.postValue(Resource.Success(
                    response.toUser())
                )
            } catch (e: HttpException) {
                _user.postValue(
                    Resource.Error(
                        e.localizedMessage ?: "An unexpected error occurred"
                    )
                )
            } catch (e: IOException) {
                _user.postValue(
                    Resource.Error(
                        e.localizedMessage
                            ?: "Couldn't reach server. Check your internet connection."
                    )
                )
            }
        }
    }

}