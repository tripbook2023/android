package com.tripbook.libs.network.model.response

import com.tripbook.libs.network.model.Author
import com.tripbook.libs.network.model.Image

data class TripNewsResponse(
    val id: Int,
    val title: String,
    val content: String,
    val author: Author, // author 데이터 모델
    val imageList: List<Image>, // 이미지 데이터 모델
    val tagList: List<String>,
    val thumbnail: Image,
    val heartNum: Int,
    val bookmarkNum: Int,
    val commentList: List<String>, //comment 데이터 모델
    val createdAt: String,
    val updatedAt: String,
    val heart: Boolean,
    val bookmark: Boolean
)