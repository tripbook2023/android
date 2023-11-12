package com.tripbook.libs.network.model.response

data class TripNewsResponse(
    val id: Int,
    val title: String,
    val content: String,
    val author: MemberSimpleDto, // author 데이터 모델
    val imageList: List<ImageResponse>, // 이미지 데이터 모델
    val tagList: List<String>,
    val thumbnail: ImageResponse,
    val heartNum: Int,
    val bookmarkNum: Int,
    val commentList: List<CommentResponse>, //comment 데이터 모델
    val createdAt: String,
    val updatedAt: String,
    val heart: Boolean,
    val bookmark: Boolean
)