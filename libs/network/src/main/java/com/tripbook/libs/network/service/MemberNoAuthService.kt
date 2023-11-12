package com.tripbook.libs.network.service

import com.tripbook.libs.network.model.response.SignUpResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface MemberNoAuthService {

    @Multipart
    @POST("signup")
    suspend fun signUp(
        @Part file: MultipartBody.Part?,
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>
    ): SignUpResponse
}