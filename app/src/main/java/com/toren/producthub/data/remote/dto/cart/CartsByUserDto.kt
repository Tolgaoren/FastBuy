package com.toren.producthub.data.remote.dto.cart

data class CartsByUserDto(
    val carts: List<Cart>,
    val limit: Int,
    val skip: Int,
    val total: Int
)