package com.tripbook.tripbook.domain.repository

import androidx.paging.PagingData
import com.tripbook.tripbook.domain.model.ArticleDetail
import com.tripbook.tripbook.domain.model.SortType
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

   fun likeArticle(articleId : Long) : Flow<Boolean>

   fun getArticleDetail(articleId : Long) : Flow<ArticleDetail?>

   fun getArticles(word: String, sortType: SortType): Flow<PagingData<ArticleDetail>>

}