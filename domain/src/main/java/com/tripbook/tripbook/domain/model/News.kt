package com.tripbook.tripbook.domain.model

data class News(
    val title: String,
    val thumbnail: String,
    val likes: Int,
    val isUserLiked: Boolean,
    val comments: Int
)