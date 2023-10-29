package com.tripbook.libs.network.service

import com.tripbook.libs.network.model.response.TokenResponse
import retrofit2.http.Header
import retrofit2.http.POST

interface TokenService {
    @POST("issue")
    suspend fun refreshToken(
        @Header("authorization") token: String
    ): TokenResponse
}