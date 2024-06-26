package com.toren.producthub.data.remote.dto.cart

data class CartProduct(
    val discountPercentage: Double,
    val discountedPrice: Int,
    val id: Int,
    val price: Double,
    val quantity: Int,
    val thumbnail: String,
    val title: String,
    val total: Double
)