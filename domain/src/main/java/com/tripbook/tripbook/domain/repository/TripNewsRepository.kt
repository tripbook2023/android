package com.tripbook.tripbook.domain.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import java.io.File

interface TripNewsRepository {

    // 여행소식 저장
    fun saveTripNews(
        context: Context,
        title: String,
        content: String,
        thumbnail: File,
        imageList: List<File>,
        tagList: List<String>?
    ): Flow<Boolean>
}