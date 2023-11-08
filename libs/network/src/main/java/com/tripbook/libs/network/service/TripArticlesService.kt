package com.tripbook.libs.network.service

import com.tripbook.libs.network.model.response.ArticleDetailResponse
import com.tripbook.libs.network.model.response.ArticleResponse
import com.tripbook.libs.network.model.response.LikeArticleResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TripArticlesService {

    @POST("{articleId}/like")
    suspend fun likeArticle(
        @Path("articleId") articledId: Long
    ): LikeArticleResponse

    @GET("{articleId}")
    suspend fun getArticleDetail(
        @Path("articleId") articledId: Long
    ) : ArticleDetailResponse

    @GET
    suspend fun getArticleList(
        @Query("word") word: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String
    ): ArticleResponse
}