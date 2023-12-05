package com.tripbook.libs.network.service

import com.tripbook.libs.network.model.response.UnitResponse
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface TripNewsService {

    @Multipart
    @POST("articles")
    suspend fun saveTripNews( // 여행소식 저장
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>, // title, content, thumbnail
        @Part("fileIds") imageList: List<Int>?,
        @Part("tagList") tagList: List<@JvmSuppressWildcards RequestBody>?,
        @Part("articleId") id: Long?
    ): UnitResponse
}