package com.tripbook.libs.network.model.response

import com.squareup.moshi.Json

data class ArticleResponse(
    @Json(name = "size")
    val size: Int,
    @Json(name = "content")
    val content: List<ArticleDetailResponse>,
    @Json(name = "number")
    val number: Int,
    @Json(name = "numberOfElements")
    val numberOfElements: Int
)