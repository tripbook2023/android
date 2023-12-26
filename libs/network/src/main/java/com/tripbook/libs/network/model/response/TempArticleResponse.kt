package com.tripbook.libs.network.model.response


data class TempArticleResponse (
    val id: Long,
    val title: String,
    val content: String,
    val tagList: List<String>?,
    val thumbnailUrl: String?,
    val location: List<LocationResponse>?,
    val createdAt: String,
    val updatedAt: String
)