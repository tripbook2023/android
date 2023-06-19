package com.tripbook.libs.network.model.response

data class ErrorResponse(
    val status: Int? = null,
    val message: List<String>? = null,
    val code: String? = null
)