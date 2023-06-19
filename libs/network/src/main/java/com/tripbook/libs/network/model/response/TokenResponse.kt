package com.tripbook.libs.network.model.response

import com.squareup.moshi.Json

data class TokenResponse(
    @Json(name = "grantType")
    val grantType: String,
    @Json(name = "accessToken")
    val accessToken: String,
    @Json(name = "refreshToken")
    val refreshToken: String
)