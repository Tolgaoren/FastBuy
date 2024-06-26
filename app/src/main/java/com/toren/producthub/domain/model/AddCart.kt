package com.toren.producthub.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddCart(
    val id: Int,
    val quantity: Int
): Parcelable
