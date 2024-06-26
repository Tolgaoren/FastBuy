package com.toren.producthub.data.remote.dto.cart

import com.toren.producthub.domain.model.AddCart

data class AddCartRequest(
    val userId: Int,
    val products: List<AddCart>
)
