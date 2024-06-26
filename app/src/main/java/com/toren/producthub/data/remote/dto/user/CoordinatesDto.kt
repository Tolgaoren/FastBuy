package com.toren.producthub.data.remote.dto.user

import com.toren.producthub.domain.model.Coordinates

data class CoordinatesDto(
    val lat: Double,
    val lng: Double
)

fun CoordinatesDto.toCoordinates() = Coordinates(
    lat = lat,
    lng = lng
)