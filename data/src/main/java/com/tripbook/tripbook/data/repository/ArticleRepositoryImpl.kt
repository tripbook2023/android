package com.tripbook.tripbook.data.repository


import com.tripbook.libs.network.NetworkResult
import com.tripbook.libs.network.safeApiCall
import com.tripbook.libs.network.service.TripArticlesService
import com.tripbook.tripbook.data.mapper.toArticle
import com.tripbook.tripbook.domain.model.Article
import com.tripbook.tripbook.domain.model.ArticleDetail
import com.tripbook.tripbook.domain.model.SortType
import com.tripbook.tripbook.domain.repository.ArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import toArticleDetail

import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val tripArticlesService: TripArticlesService
) : ArticleRepository {
    override fun likeArticle(articleId: Long): Flow<Boolean>  = safeApiCall(Dispatchers.IO) {
        tripArticlesService.likeArticle(articleId)
    }.map {
        when(it) {
            is NetworkResult.Success -> {
                true
            } else -> false
        }
    }

    override fun getArticleDetail(articleId: Long): Flow<ArticleDetail?> = safeApiCall(Dispatchers.IO) {
        tripArticlesService.getArticleDetail(articleId)
    }.map { response ->
        when (response) {
            is NetworkResult.Success -> {
                response.value.toArticleDetail()
            }
            else -> null
        }
    }

    override fun getArticles(
        word: String,
        page: Int,
        size: Int,
        sortType: SortType
    ): Flow<Article?> = safeApiCall(Dispatchers.IO) {
        tripArticlesService.getArticleList(word, page, size, sortType.name)
    }.map { response ->
        when(response) {
            is NetworkResult.Success -> {
                response.value.toArticle()
            }
            else -> null
        }
    }

}