package com.toren.producthub.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val comment: String = "",
    val date: String = "",
    val rating: Int = 0,
    val reviewerEmail: String = "",
    val reviewerName: String = ""
): Parcelable
