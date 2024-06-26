package com.toren.producthub.presentation.orders

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.producthub.common.UserSessionManager
import com.toren.producthub.data.remote.dto.auth.AuthResponse
import com.toren.producthub.data.remote.dto.cart.ProductDto
import com.toren.producthub.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel() {

    private lateinit var userSessionManager: AuthResponse

    private val _orders = MutableLiveData<List<ProductDto>>()
    val orders: LiveData<List<ProductDto>> = _orders

    init {
        UserSessionManager.getCurrentUser()?.let {
            userSessionManager = it
        }
        getOrders()
    }

    private fun getOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userSessionManager.let {
                    val data = productRepository.getCartByUser(it.id)
                    println(data)
                    _orders.postValue(data.carts[0].products)
                }
            } catch (e: Exception) {
                Log.e("LikeRepository", e.localizedMessage
                    ?: "An unexpected error occurred")
            }
        }
    }

}