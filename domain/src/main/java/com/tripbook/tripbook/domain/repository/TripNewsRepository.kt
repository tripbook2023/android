package com.tripbook.tripbook.domain.repository

import kotlinx.coroutines.flow.Flow

interface TripNewsRepository {

    // 여행소식 저장
    fun saveTripNews(
        id: Long?,
        title: String,
        content: String,
        thumbnail: String,
        imageList: List<Int>,
        tagList: List<String>?
    ): Flow<Boolean>

}