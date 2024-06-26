package com.toren.producthub.presentation.search_result

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
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel
@Inject constructor(
    private val repository: ProductRepository,
    private val likeRepository: LikeRepository
) : ViewModel() {
    private lateinit var userSessionManager: AuthResponse

    private val _searchProducts = MutableLiveData<List<Product>>()
    val searchProducts: LiveData<List<Product>> = _searchProducts

    private val _likes = MutableLiveData<Resource<List<Like>>>()
    val likes: LiveData<Resource<List<Like>>> = _likes

    init {
        UserSessionManager.getCurrentUser()?.let {
            userSessionManager = it
        }
        getLikes()
    }

    fun searchProducts(query: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.searchProducts(query)
            }
            _searchProducts.postValue(result.products.map {
                it.toProduct()
            })
        }
    }

    fun getLikes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val likes = likeRepository.getLikesByUserId(userSessionManager.id)
                _likes.postValue(Resource.Success(likes))
            } catch (e: Exception) {
                _likes.postValue(Resource.Error(
                    e.localizedMessage ?: "An unexpected error occurred"))
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