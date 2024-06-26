package com.toren.producthub.data.remote.dto.cart

data class Cart(
    val discountedTotal: Double,
    val id: Int,
    val products: List<ProductDto>,
    val total: Double,
    val totalProducts: Int,
    val totalQuantity: Int,
    val userId: Int
)