package com.tripbook.libs.network.model.request

import com.tripbook.libs.network.model.response.LocationResponse

data class ArticleRequest (
    val articleId: Long?,
    val title: String,
    val content: String,
    val fileIds: List<Int>?,
    val thumbnail: String?,
    val locationList: List<LocationResponse>?
)