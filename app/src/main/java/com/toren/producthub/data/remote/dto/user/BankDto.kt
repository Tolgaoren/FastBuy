package com.toren.producthub.data.remote.dto.user

import com.toren.producthub.domain.model.Bank

data class BankDto(
    val cardExpire: String,
    val cardNumber: String,
    val cardType: String,
    val currency: String,
    val iban: String
)

fun BankDto.toBank() = Bank(
    cardExpire = cardExpire,
    cardNumber = cardNumber,
    cardType = cardType,
    currency = currency,
    iban = iban
)