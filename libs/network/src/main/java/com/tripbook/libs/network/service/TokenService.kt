package com.tripbook.libs.network.service

import com.tripbook.libs.network.model.response.TokenResponse
import retrofit2.http.GET

interface TokenService {
    @GET("issue/")
    suspend fun refreshToken(): TokenResponse
}