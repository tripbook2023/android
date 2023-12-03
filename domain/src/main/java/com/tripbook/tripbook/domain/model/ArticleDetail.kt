package com.tripbook.tripbook.domain.model

data class ArticleDetail(
    val id: Long,
    val title: String,
    val content: String,
    val author: MemberSimple,
    val imageList: List<Image>?,
    val thumbnail: Image?,
    val tagList: List<String>,
    val numberOfHearts: Long,
    val numberOfBookmarks: Long,
    val commentList: List<Comment>,
    val createdAt: String,
    val updatedAt: String,
    val heart: Boolean,
    val bookmark: Boolean
)

data class MemberSimple(
    val id: Long,
    val name: String,
    val profileUrl: String,
    val role: String
)

data class Image(
    val id: Long,
    val url: String
)

data class Comment(
    val id: Long,
    val content: String,
    val author: MemberSimple,
    val childList: List<Any>, //여기 댓글 추후 수정해야 됨
    val createdAt: String,
    val updatedAt: String
)

