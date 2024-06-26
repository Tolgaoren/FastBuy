package com.toren.producthub.presentation.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.producthub.common.UserSessionManager
import com.toren.producthub.data.local.entity.Like
import com.toren.producthub.data.local.entity.VisitHistory
import com.toren.producthub.data.remote.dto.auth.AuthResponse
import com.toren.producthub.data.repository.LikeRepository
import com.toren.producthub.data.repository.VisitHistoryRepository
import com.toren.producthub.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject constructor(
    private val likeRepository: LikeRepository,
    private val visitHistoryRepository: VisitHistoryRepository
) : ViewModel() {

    private lateinit var userSessionManager: AuthResponse

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    private val _like = MutableLiveData<Like?>(null)
    val like: LiveData<Like?> = _like

    init {
        UserSessionManager.getCurrentUser()?.let {
            userSessionManager = it
        }
    }

    fun setProduct(product: Product) {
        _product.value = product
        saveVisitHistory(product.id)
    }

    fun getLike(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val likeItem = likeRepository.getLike(
                    userSessionManager.id,
                    productId
                )
                if (likeItem != null) {
                    _like.postValue(likeItem)
                } else {
                    _like.postValue(null)
                }
            } catch (e: Exception) {
                Log.e(
                    "LikeRepository", e.localizedMessage
                        ?: "An unexpected error occurred"
                )
            }
        }
    }

    fun likeBtnClicked(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            println(like.value)
            try {
                if (like.value != null) {
                    val process = likeRepository.deleteLike(
                        like.value!!
                    )
                    process.let {
                        _like.postValue(null)
                    }
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
                        _like.postValue(it)
                    }
                }
            } catch (e: Exception) {
                Log.e("LikeRepository", e.localizedMessage ?: "An unexpected error occurred")
            }
        }
    }

    private fun saveVisitHistory(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                visitHistoryRepository.insertVisitHistory(VisitHistory(
                    userId = userSessionManager.id,
                    productId = productId
                ))
            } catch (e: Exception) {
                Log.e("VisitHistoryRepository", e.localizedMessage ?: "An unexpected error occurred")
            }
        }
    }

}