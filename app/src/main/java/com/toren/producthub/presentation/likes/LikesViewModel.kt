package com.toren.producthub.presentation.likes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.producthub.common.Resource
import com.toren.producthub.common.UserSessionManager
import com.toren.producthub.data.local.entity.Like
import com.toren.producthub.data.remote.dto.auth.AuthResponse
import com.toren.producthub.data.remote.dto.product.toProduct
import com.toren.producthub.data.repository.LikeRepository
import com.toren.producthub.domain.model.Product
import com.toren.producthub.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LikesViewModel @Inject constructor(
    private val likeRepository: LikeRepository,
    private val productRepository: ProductRepository
): ViewModel() {

    private lateinit var userSessionManager: AuthResponse

    private val _products = MutableLiveData<Resource<List<Product>>>()
    val products: LiveData<Resource<List<Product>>> = _products

    private val _likes = MutableLiveData<Resource<List<Like>>>()
    val likes: LiveData<Resource<List<Like>>> = _likes

    init {
        UserSessionManager.getCurrentUser()?.let {
        userSessionManager = it
        }
        getLikes()
    }


    fun getLikes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val likes = likeRepository.getLikesByUserId(userSessionManager.id)
                _likes.postValue(Resource.Success(likes))
                val currentProducts = mutableListOf<Product>()
                likes.forEach { like ->
                    try {
                        val product = productRepository.getProduct(like.productId)
                        currentProducts.add(product.toProduct())
                        _products.postValue(Resource.Success(currentProducts.toList()))
                    } catch (e: Exception) {
                        Log.e("ProductViewModel", "Error loading product with ID: ${like.productId}", e)
                    }
                }
            } catch (e: HttpException) {
                _products.postValue(Resource.Error(
                    e.localizedMessage ?: "An unexpected error occurred"))
            }catch (e: IOException) {
                _products.postValue(
                    Resource.Error(
                        e.localizedMessage
                            ?: "Couldn't reach server. Check your internet connection."
                    )
                )
            }
        }
    }
    fun likeBtnClicked(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val findLike = likes.value?.data?.find { it.productId == productId }
                if (findLike != null) {
                    likeRepository.deleteLike(findLike)
                    getLikes()
                } else {
                    likeRepository.insertLike(
                        Like(
                            userId = userSessionManager.id,
                            productId = productId
                        )
                    )
                    val likeItem = likeRepository.getLike(
                        userSessionManager.id,
                        productId
                    )
                    likeItem?.let {
                        getLikes()
                    }
                }
            } catch (e: Exception) {
                Log.e("LikeRepository", e.localizedMessage ?: "An unexpected error occurred")
            }
        }
    }

}