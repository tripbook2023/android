package com.tripbook.tripbook.data.mapper

import com.tripbook.libs.network.model.response.LoginResponse
import com.tripbook.tripbook.domain.model.UserAuth
import com.tripbook.tripbook.domain.model.UserLoginStatus

fun LoginResponse.toUserAuth() = UserAuth(
    nickname = nickname ?: "",
    accessToken = accessToken,
    refreshToken = refreshToken,
    status = UserLoginStatus.from(status),
    email = email ?: ""
)