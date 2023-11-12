package com.tripbook.tripbook.data.mapper

import com.tripbook.libs.network.model.response.ArticleResponse
import com.tripbook.tripbook.domain.model.Article
import toArticleDetail

fun ArticleResponse.toArticle(): Article = Article(
    size, content = content.map { it.toArticleDetail() }, number, numberOfElements)