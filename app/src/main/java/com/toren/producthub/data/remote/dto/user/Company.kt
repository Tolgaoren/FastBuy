package com.toren.producthub.data.remote.dto.user

data class Company(
    val address: AddressDto,
    val department: String,
    val name: String,
    val title: String
)