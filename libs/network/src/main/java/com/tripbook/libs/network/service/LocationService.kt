package com.tripbook.libs.network.service

import com.tripbook.libs.network.model.response.LocationDocResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface LocationService {

    // https://dapi.kakao.com/v2/local/search/keyword.json
    @GET("v2/local/search/keyword.json")
    suspend fun searchLocation(
        @Header("Authorization") key: String, // REST API 키
        @Query("query") query: String // 질의어
    ): LocationDocResponse
}