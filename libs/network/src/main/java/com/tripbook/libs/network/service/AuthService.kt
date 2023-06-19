package com.tripbook.libs.network.service

import retrofit2.http.GET

interface AuthService {
    @GET("/oauth2")
    suspend fun validateToken()
}