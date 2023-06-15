package com.tripbook.libs.network

import retrofit2.http.GET

interface TokenService {
    @GET("/token/issue")
    suspend fun refreshToken(): UserTokenDto

    @GET("/login/oauth2")
    suspend fun signUp(): UserTokenDto
}