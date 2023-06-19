package com.tripbook.libs.network.model.response

import com.squareup.moshi.Json

data class LoginResponse(
    @Json(name = "nickname")
    val nickname: String?,
    @Json(name = "accessToken")
    val accessToken: String?,
    @Json(name = "refreshToken")
    val refreshToken: String?,
    @Json(name = "status")
    val status: String?,
    @Json(name = "email")
    val email: String?,
)