package com.tripbook.libs.network.service

import com.tripbook.libs.network.UserTokenDto
import com.tripbook.libs.network.model.response.LoginResponse
import com.tripbook.libs.network.model.request.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @POST("/member/signup")
    suspend fun signUp(@Body requestBody: SignUpRequest): UserTokenDto

    @GET("/member/nickname/validate")
    suspend fun validateNickname(@Query("name") nickname: String): LoginResponse
}