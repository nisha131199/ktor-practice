package com.example.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest (
    val phone: String? = null,
    val password: String? = null
)