package com.toren.producthub.data.remote.dto.product

import com.toren.producthub.domain.model.Product

data class ProductResponse(
    val availabilityStatus: String = "",
    val brand: String = "",
    val category: String = "",
    val description: String = "",
    val dimensions: Dimensions = Dimensions(),
    val discountPercentage: Double = 0.0,
    val id: Int = 0,
    val images: List<String> = listOf(),
    val meta: Meta = Meta(),
    val minimumOrderQuantity: Int = 0,
    val price: Double = 0.0,
    val rating: Double = 0.0,
    val returnPolicy: String = "",
    val reviews: List<ReviewDto> = listOf(),
    val shippingInformation: String = "",
    val sku: String = "",
    val stock: Int = 0,
    val tags: List<String> = listOf(),
    val thumbnail: String = "",
    val title: String = "",
    val warrantyInformation: String = "",
    val weight: Int = 0
)


fun ProductResponse.toProduct(): Product {
    return Product(
        availabilityStatus = availabilityStatus,
        brand = brand,
        category = category,
        description = description,
        discountPercentage = discountPercentage,
        id = id,
        images = images,
        minimumOrderQuantity = minimumOrderQuantity,
        price = price,
        rating = rating,
        returnPolicy = returnPolicy,
        reviews = reviews.map { it.toReview() },
        shippingInformation = shippingInformation,
        stock = stock,
        tags = tags,
        thumbnail = thumbnail,
        title = title,
        warrantyInformation = warrantyInformation
    )
}
