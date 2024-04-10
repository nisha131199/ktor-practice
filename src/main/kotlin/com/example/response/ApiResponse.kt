package com.example.response

import io.ktor.http.HttpStatusCode

data class ApiResponse (
    val error: Boolean = true,
    val status: Int = HttpStatusCode.BadRequest.value,
    val message: String? = null,
    val serverError: String? = null,
    val data: Any? = null
)