package com.tripbook.libs.network.service

import com.tripbook.libs.network.model.response.MemberResponse
import com.tripbook.libs.network.model.response.UnitResponse
import com.tripbook.libs.network.model.response.UpdateMemberResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query

interface MemberService {

    @GET("nickname/validate")
    suspend fun validateNickname(
        @Query("name") nickname: String
    ): UnitResponse

    @GET("select")
    suspend fun getMember(): MemberResponse

    @Multipart
    @POST("update")
    suspend fun updateMember(
        @Part file : MultipartBody.Part?,
        @PartMap params : Map<String, @JvmSuppressWildcards RequestBody>
    ) : UpdateMemberResponse
}