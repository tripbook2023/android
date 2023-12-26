package com.tripbook.tripbook.data.mapper

import com.tripbook.libs.network.model.response.TempArticleResponse
import com.tripbook.tripbook.domain.model.TempArticle

fun TempArticleResponse.toTempArticle() = TempArticle(
    id = id,
    title = title,
    content = content,
    thumbnailUrl = thumbnailUrl,
    location = location?.map { it.toLocation() },
    tagList = tagList,
    createdAt = createdAt,
    updatedAt = updatedAt
)