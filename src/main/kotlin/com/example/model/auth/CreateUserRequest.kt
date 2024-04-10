package com.example.model.auth

data class CreateUserRequest (
    val name: String? = null,
    val phone: String? = null,
    val password: String? = null
)