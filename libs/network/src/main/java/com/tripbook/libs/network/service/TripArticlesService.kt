package com.tripbook.libs.network.service

import com.tripbook.libs.network.model.response.ArticleDetailResponse
import com.tripbook.libs.network.model.response.ArticleResponse
import com.tripbook.libs.network.model.response.LikeArticleResponse
import com.tripbook.libs.network.model.response.TempArticleResponse
import com.tripbook.libs.network.model.response.UnitResponse
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Query

interface TripArticlesService {

    @POST("articles/{articleId}/like")
    suspend fun likeArticle(
        @Path("articleId") articledId: Long
    ): LikeArticleResponse

    @GET("articles/{articleId}")
    suspend fun getArticleDetail(
        @Path("articleId") articledId: Long
    ) : ArticleDetailResponse

    @GET("articles")
    suspend fun getArticleList(
        @Query("word") word: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String
    ): ArticleResponse

    @DELETE("{articleId}")
    suspend fun deleteArticle(
        @Path("articleId") articledId: Long
    ) : UnitResponse

    @Multipart
    @POST("articles/temp")
    suspend fun tempSaveArticle(
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody?>, // title, content
        @Part("thumbnail") thumbnail: @JvmSuppressWildcards RequestBody?,
        @Part("tagList") tagList: List<@JvmSuppressWildcards RequestBody>?,
        @Part("fileIds") imageList: List<Int>?,
        @Part("articleId") id: Long?
    ): TempArticleResponse

    @Multipart
    @POST("articles")
    suspend fun saveTripNews( // 여행소식 저장
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>, // title, content, thumbnail
        @Part("fileIds") imageList: List<Int>?,
        @Part("tagList") tagList: List<@JvmSuppressWildcards RequestBody>?,
        @Part("articleId") id: Long?
    ): ArticleDetailResponse
}