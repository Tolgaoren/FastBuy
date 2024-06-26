package com.toren.producthub.presentation.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.producthub.common.Resource
import com.toren.producthub.common.UserSessionManager
import com.toren.producthub.data.local.entity.SearchHistory
import com.toren.producthub.data.remote.dto.auth.AuthResponse
import com.toren.producthub.data.remote.dto.product.toProduct
import com.toren.producthub.data.repository.SearchHistoryRepository
import com.toren.producthub.data.repository.VisitHistoryRepository
import com.toren.producthub.domain.model.Product
import com.toren.producthub.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchHistoryRepository: SearchHistoryRepository,
    private val visitHistoryRepository: VisitHistoryRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private lateinit var userSessionManager: AuthResponse

    private val _searchHistory = MutableLiveData<Resource<List<SearchHistory>>>()
    val searchHistory: LiveData<Resource<List<SearchHistory>>> = _searchHistory

    private val _products = MutableLiveData<Resource<List<Product>>>()
    val products: LiveData<Resource<List<Product>>> = _products

    init {
        UserSessionManager.getCurrentUser()?.let {
            userSessionManager = it
        }
        getSearchHistories()
        getVisitHistory()
    }

    private fun getSearchHistories() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val searchHistories = searchHistoryRepository.getSearchHistoriesByUserId(userSessionManager.id)
                _searchHistory.postValue(Resource.Success(searchHistories.sortedByDescending { it.id }))
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Error getting search histories: ${e.message}")
            }
        }
    }

    fun addSearchHistory(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val searchHistory = SearchHistory(userId = userSessionManager.id, searchQuery = query)
                searchHistoryRepository.insertSearchHistory(searchHistory)
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Error adding search history: ${e.message}")
            }
        }
    }

    fun deleteSearchHistory(searchItem: SearchHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                searchHistoryRepository.deleteSearchHistory(searchItem)
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Error deleting search history: ${e.message}")
            }
        }
    }

    private fun getVisitHistory() {
        viewModelScope.launch {
            try {
                val ids = visitHistoryRepository.getVisitHistoriesByUserId(userSessionManager.id).sortedByDescending { it.id }.map { it.productId }
                val products = mutableListOf<Product>()
                ids.forEach { id ->
                    val productList = productRepository.getProduct(id)
                    productList.let {
                        products.add(it.toProduct())
                    }
                }
                println(products)
                _products.postValue(Resource.Success(products))

            } catch (e: Exception) {
                Log.e("SearchViewModel", "Error getting search histories: ${e.message}")
            }
        }
    }

}