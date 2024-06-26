package com.toren.producthub.data.remote.dto.product

import com.toren.producthub.domain.model.Review
import com.toren.producthub.utils.TimeFormatter

data class ReviewDto(
    val comment: String = "",
    val date: String = "",
    val rating: Int = 0,
    val reviewerEmail: String = "",
    val reviewerName: String = ""
)


fun ReviewDto.toReview(): Review {
    return Review(
        comment = comment,
        date = TimeFormatter.formatDateTime(date),
        rating = rating,
        reviewerEmail = reviewerEmail,
        reviewerName = reviewerName
    )
}