package com.tripbook.tripbook.domain.model

data class ArticleDetail(
    val id: Long,
    val title: String,
    val content: String,
    val author: MemberSimple,
    val thumbnailUrl: String?,
   // val tagList: List<String>,
    val heartNum: Long,
    val bookmarkNum: Long,
    val location : List<LocationList>,
    val commentList: List<Comment>,
    val createdAt: String,
    val updatedAt: String,
    val heart: Boolean,
    val bookmark: Boolean
)

data class MemberSimple(
    val id: Long,
    val name: String,
    val profileUrl: String?,
    val role: String
)

data class LocationList(
    val id : Long,
    val locationX : String,
    val locationY : String,
    val name : String
)


data class Comment(
    val id: Long,
    val content: String,
    val author: MemberSimple,
    val childList: String, //여기 댓글 추후 수정해야 됨
    val createdAt: String,
    val updatedAt: String
)

