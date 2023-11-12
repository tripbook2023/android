package com.tripbook.tripbook.domain.repository

import com.tripbook.tripbook.domain.model.Article
import com.tripbook.tripbook.domain.model.ArticleDetail
import com.tripbook.tripbook.domain.model.SortType
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

   fun likeArticle(articleId : Long) : Flow<Boolean>

   fun getArticleDetail(articleId : Long) : Flow<ArticleDetail?>

   fun getArticles(word: String, page: Int = 0, size: Int = 10, sortType: SortType): Flow<Article?>

}