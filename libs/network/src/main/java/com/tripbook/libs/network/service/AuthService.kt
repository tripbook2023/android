package com.tripbook.libs.network.service

import com.tripbook.libs.network.model.response.LoginResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface AuthService {
    @GET("oauth2/")
    suspend fun validateToken(
        @Header("Authorization") accessToken: String
    ): LoginResponse
}