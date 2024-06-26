package com.toren.producthub.utils

class CreditCartNumberFormatter {
    fun formatCreditCardNumber(cardNumber: String): String {
        val maskedSection = cardNumber.dropLast(4).replace(Regex("."), "*")
        val visibleSection = cardNumber.takeLast(4)
        val formattedNumber = (maskedSection + visibleSection).chunked(4).joinToString(" ")
        return formattedNumber
    }
}