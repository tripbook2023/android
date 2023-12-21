package com.tripbook.tripbook.domain.model

data class LikeArticle(
    val id: Long,
    val heartNum: Long,
    val bookmarkNum: Long,
    val heart: Boolean,
    val bookmark: Boolean
)