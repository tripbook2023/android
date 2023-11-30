package com.tripbook.libs.network.service

import com.tripbook.libs.network.model.response.ArticleDetailResponse
import com.tripbook.libs.network.model.response.ArticleResponse
import com.tripbook.libs.network.model.response.LikeArticleResponse
import retrofit2.http.GET
import retrofit2.http.POST
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
}