package com.toren.producthub.data.remote.dto.user

import com.toren.producthub.domain.model.Address

data class AddressDto(
    val address: String,
    val city: String,
    val coordinates: CoordinatesDto,
    val country: String,
    val postalCode: String,
    val state: String,
    val stateCode: String
)

fun AddressDto.toAddress() = Address (
        address = address,
        city = city,
        coordinates = coordinates.toCoordinates(),
        country = country,
        postalCode = postalCode,
        state = state,
        stateCode = stateCode
    )