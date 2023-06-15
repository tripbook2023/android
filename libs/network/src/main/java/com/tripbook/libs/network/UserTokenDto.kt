package com.tripbook.libs.network

import com.squareup.moshi.Json

data class UserTokenDto(
    @Json(name = "nickname")
    val name: String?,
    @Json(name = "accessToken")
    val accessToken: String,
    @Json(name = "refreshToken")
    val refreshToken: String,
    @Json(name = "status")
    val status: String?,
    @Json(name = "email")
    val email: String?
)