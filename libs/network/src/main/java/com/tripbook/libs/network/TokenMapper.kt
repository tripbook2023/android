package com.tripbook.libs.network

import com.tripbook.database.Token

fun UserTokenDto.toToken() = Token(
    accessToken, refreshToken
)