package com.tripbook.libs.network.model.response

data class LikeArticleResponse (
    val id: Long,
    val heartNum: Long,
    val bookmarkNum: Long,
    val heart: Boolean,
    val bookmark: Boolean
)