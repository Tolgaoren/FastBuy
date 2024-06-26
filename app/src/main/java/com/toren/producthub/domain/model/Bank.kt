package com.toren.producthub.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Bank(
    val cardExpire: String,
    val cardNumber: String,
    val cardType: String,
    val currency: String,
    val iban: String
): Parcelable
