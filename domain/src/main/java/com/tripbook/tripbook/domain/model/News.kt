package com.tripbook.tripbook.domain.model

data class News(
    val title: String,
    val thumbnail: String,
    val likes: Int,
    val isUserLiked: Boolean,
    val comments: Int,
    val applyStatus: NewsApplyStatus,
    val uploadDate: String // 나중에 kotlinx.datetime 으로 변경 예정.
)