package com.toren.producthub.data.remote.dto.user

import com.toren.producthub.domain.model.User

data class UserDto(
    val address: AddressDto,
    val age: Int,
    val bank: BankDto,
    val birthDate: String,
    val bloodGroup: String,
    val company: Company,
    val crypto: Crypto,
    val ein: String,
    val email: String,
    val eyeColor: String,
    val firstName: String,
    val gender: String,
    val hair: Hair,
    val height: Double,
    val id: Int,
    val image: String,
    val ip: String,
    val lastName: String,
    val macAddress: String,
    val maidenName: String,
    val password: String,
    val phone: String,
    val role: String,
    val ssn: String,
    val university: String,
    val userAgent: String,
    val username: String,
    val weight: Double
)

fun UserDto.toUser() = User(
        address = address.toAddress(),
        bank = bank.toBank(),
        birthDate = birthDate,
        email = email,
        firstName = firstName,
        id = id,
        image = image,
        lastName = lastName,
        phone = phone,
        username = username
)