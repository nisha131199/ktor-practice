package com.example.dto

data class User (
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
) {
    fun getUserData() = UserResponse (
        id = id,
        name = name,
        email = email,
        phone = phone
    )
}

data class UserResponse(
    val id: Int? = null,
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
)