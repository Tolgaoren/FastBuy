package com.toren.producthub.data.remote.dto.product

data class ProductsDto(
    val limit: Int = 0,
    val products: List<ProductResponse> = emptyList(),
    val skip: Int = 0,
    val total: Int = 0
)