package com.tripbook.tripbook.data.repository


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tripbook.libs.network.NetworkResult
import com.tripbook.libs.network.model.request.ArticleRequest
import com.tripbook.libs.network.safeApiCall
import com.tripbook.libs.network.service.TripArticlesService
import com.tripbook.tripbook.data.datasource.ArticlePagingSource
import com.tripbook.tripbook.data.mapper.toLocationResponse
import com.tripbook.tripbook.domain.model.ArticleDetail
import com.tripbook.tripbook.domain.model.Location
import com.tripbook.tripbook.domain.model.LikeArticle
import com.tripbook.tripbook.domain.model.SortType
import com.tripbook.tripbook.domain.repository.ArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import toArticleDetail
import toLikeArticle
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val tripArticlesService: TripArticlesService
) : ArticleRepository {
    override fun likeArticle(articleId: Long): Flow<LikeArticle?> = safeApiCall(Dispatchers.IO) {
        tripArticlesService.likeArticle(articleId)
    }.map {
        when (it) {
            is NetworkResult.Success -> {
                val likeArticle = it.value.toLikeArticle()
                likeArticle

            }
            else -> null
        }
    }

    override fun getArticleDetail(articleId: Long): Flow<ArticleDetail?> =
        safeApiCall(Dispatchers.IO) {
            tripArticlesService.getArticleDetail(articleId)
        }.map { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.value.toArticleDetail()
                }

                else -> null
            }
        }

    override fun getArticles(word: String, sortType: SortType): Flow<PagingData<ArticleDetail>> =
        Pager(
            PagingConfig(pageSize = 10)
        ) {
            ArticlePagingSource(word, sortType, tripArticlesService)
        }.flow

    override fun deleteArticle(articleId: Long): Flow<Boolean> = safeApiCall(Dispatchers.IO) {
        tripArticlesService.deleteArticle(articleId)
    }.map { response ->
        when (response) {
            is NetworkResult.Success -> {
                response.value
                true
            }

            else -> {
                false
            }
        }
    }

    override fun saveTempArticle(
        tempId: Long?,
        title: String,
        content: String,
        thumbnail: String?,
        imageList: List<Int>?,
        locationList: List<Location>?
    ): Flow<Long?> = safeApiCall(Dispatchers.IO) {
        val articleResponse = ArticleRequest(
            tempId,
            title,
            content,
            fileIds = imageList,
            thumbnail,
            locationList?.map { it.toLocationResponse() }
        )

        tripArticlesService.tempSaveArticle(
            articleResponse
        )
    }.map {
        when (it) {
            is NetworkResult.Success -> {
                it.value.id // 임시저장 게시글 아이디 저장에 사용
            }

            else -> {
                null
            }
        }
    }

    override fun saveArticle(
        id: Long?,
        title: String,
        content: String,
        thumbnail: String,
        imageList: List<Int>,
        locationList: List<Location>?
    ): Flow<Long?> = safeApiCall(Dispatchers.IO) {
        val articleResponse = ArticleRequest(
            id,
            title,
            content,
            fileIds = imageList,
            thumbnail,
            locationList?.map { it.toLocationResponse() }
        )
        tripArticlesService.saveTripNews(
            articleResponse
        )
    }.map {
        when (it) {
            is NetworkResult.Success -> {
                it.value.id
            }

            else -> {
                null
            }
        }
    }

}