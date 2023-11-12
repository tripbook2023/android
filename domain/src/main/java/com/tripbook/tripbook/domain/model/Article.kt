package com.tripbook.tripbook.domain.model

data class Article(
    val size: Int,
    val content: List<ArticleDetail>,
    val number: Int,
    val numberOfElement: Int,
)