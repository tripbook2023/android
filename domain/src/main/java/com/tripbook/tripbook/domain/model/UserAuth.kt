package com.tripbook.tripbook.domain.model

data class UserAuth(
    val nickname: String,
    val accessToken: String?,
    val refreshToken: String?,
    val status: UserLoginStatus,
    val email: String,
)