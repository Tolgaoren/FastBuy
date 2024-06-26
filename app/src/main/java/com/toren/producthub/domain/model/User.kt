package com.toren.producthub.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val address: Address,
    val bank: Bank,
    val birthDate: String,
    val email: String,
    val firstName: String,
    val id: Int,
    val image: String,
    val lastName: String,
    val phone: String,
    val username: String
): Parcelable
