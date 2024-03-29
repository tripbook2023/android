package com.tripbook.libs.network.model.response

import com.squareup.moshi.Json

data class ArticleDetailResponse(
    val id: Long,
    val title: String,
    val content: String,
    val author: MemberSimpleDto,
    val thumbnailUrl: String?,
    val tagList: List<String>?,
    val location: List<LocationResponse>?,
    @Json(name = "heartNum")
    val numberOfHearts: Long,
    @Json(name = "bookmarkNum")
    val numberOfBookmarks: Long,
    val commentList: List<CommentResponse>,
    val createdAt: String,
    val updatedAt: String,
    val heart: Boolean,
    val bookmark: Boolean
)

data class MemberSimpleDto(
    val id: Long,
    val name: String,
    val profileUrl: String?,
    val role: String
)

data class CommentResponse(
    val id: Long,
    val content: String,
    val author: MemberSimpleDto,
    val childList: String,
    val createdAt: String,
    val updatedAt: String
)


