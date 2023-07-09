package com.tripbook.libs.network.model.response

data class UnitResponse(
    val status: String? = null,
    val message: List<String>? = null,
    val code: Int? = null
)

