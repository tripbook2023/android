package com.tripbook.libs.network.model.response

import com.squareup.moshi.Json

data class LikeArticleResponse (
    @Json(name = "id")
    val id: Long?,
    @Json(name = "heartNum")
    val heartNum: Long?,
    @Json(name = "bookmarkNum")
    val bookmarkNum: Long?,
    @Json(name = "heart")
    val heart: Boolean?,
    @Json(name = "bookmark")
    val bookmark: Boolean?,
)