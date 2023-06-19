package com.tripbook.libs.network.model.response

import com.squareup.moshi.Json

data class SignUpResponse(
    @Json(name = "accessToken")
    val accessToken: String,
    @Json(name = "refreshToken")
    val refreshToken: String,
    @Json(name = "message")
    val message: String,
)