package com.tripbook.libs.network.service

import com.tripbook.libs.network.model.response.ImageResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface CommonService {

    @Multipart
    @POST("image/upload")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
        @Query("category") category: String
    ): ImageResponse

}