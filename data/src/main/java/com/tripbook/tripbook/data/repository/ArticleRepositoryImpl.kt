package com.tripbook.tripbook.data.repository


import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tripbook.libs.network.NetworkResult
import com.tripbook.libs.network.safeApiCall
import com.tripbook.libs.network.service.TripArticlesService
import com.tripbook.tripbook.data.datasource.ArticlePagingSource
import com.tripbook.tripbook.domain.model.ArticleDetail
import com.tripbook.tripbook.domain.model.LikeArticle
import com.tripbook.tripbook.domain.model.SortType
import com.tripbook.tripbook.domain.repository.ArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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

    override fun getArticles(word: String, sortType: SortType): Flow<PagingData<ArticleDetail>> = Pager(
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
        tagList: List<String>?
    ): Flow<Long?> = safeApiCall(Dispatchers.IO) {
        val titleBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val contentBody = content.toRequestBody("text/plain".toMediaTypeOrNull())
        val thumbnailBody = thumbnail?.toRequestBody("text/plain".toMediaTypeOrNull())
        val tagListBody: MutableList<RequestBody> = mutableListOf()
        tagList?.map {
            val tagBody = it.toRequestBody("text/plain".toMediaTypeOrNull())
            tagListBody.add(tagBody)
        }

        // 임시저장은 썸네일이 nullable해 map에 담지 않고 따로 전송
        tripArticlesService.tempSaveArticle(
            mapOf(
                "title" to titleBody,
                "content" to contentBody,
            ),
            thumbnailBody,
            tagListBody,
            imageList,
            tempId
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
        tagList: List<String>?
    ): Flow<Long?> = safeApiCall(Dispatchers.IO) {
        val titleBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val contentBody = content.toRequestBody("text/plain".toMediaTypeOrNull())
        val thumbnailBody = thumbnail.toRequestBody("text/plain".toMediaTypeOrNull())
        val tagListBody: MutableList<RequestBody> = mutableListOf()

        tagList?.map {
            val tagBody = it.toRequestBody("text/plain".toMediaTypeOrNull())
            tagListBody.add(tagBody)
        }

        tripArticlesService.saveTripNews(
            mapOf(
                "title" to titleBody,
                "content" to contentBody,
                "thumbnail" to thumbnailBody
            ),
            imageList,
            tagListBody,
            id
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