package com.toren.producthub.presentation.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.producthub.data.remote.dto.category.Category
import com.toren.producthub.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel
    @Inject constructor(
    private val productRepository: ProductRepository
    ): ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

        init {
            getCategories()
        }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = productRepository.getCategories()
            _categories.postValue(data.toList())
        }
    }
}