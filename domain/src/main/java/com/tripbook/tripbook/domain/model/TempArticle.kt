package com.tripbook.tripbook.domain.model

data class TempArticle (
    val id: Long,
    val title: String,
    val content: String,
    val tagList: List<String>?,
    val thumbnailUrl: String?,
    val createdAt: String,
    val updatedAt: String
)