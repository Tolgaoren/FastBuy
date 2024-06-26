package com.toren.producthub.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val address: String,
    val city: String,
    val coordinates: Coordinates,
    val country: String,
    val postalCode: String,
    val state: String,
    val stateCode: String): Parcelable
