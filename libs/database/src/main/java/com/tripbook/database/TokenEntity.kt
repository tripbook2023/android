package com.tripbook.database

data class TokenEntity(
    val accessToken: String,
    val refreshToken: String?
)