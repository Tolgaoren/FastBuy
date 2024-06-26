package com.toren.producthub.data.remote.dto.cart

data class AddCartResponse(
    val discountedTotal: Double,
    val id: Int,
    val products: List<CartProduct>,
    val total: Double,
    val totalProducts: Int,
    val totalQuantity: Int,
    val userId: Int
)