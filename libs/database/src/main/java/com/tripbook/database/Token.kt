package com.tripbook.database

data class Token(
    val accessToken: String,
    val refreshToken: String?,
)