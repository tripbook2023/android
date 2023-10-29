package com.tripbook.tripbook.domain.usecase

import android.content.Context
import com.tripbook.tripbook.domain.repository.TripNewsRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class SaveTripNewsUseCase @Inject constructor(
    private val repository: TripNewsRepository
) {

    operator fun invoke(
        context: Context,
        title: String,
        content: String,
        thumbnail: File,
        imageList: List<File>,
        tagList: List<String>?
    ): Flow<Boolean> = repository.saveTripNews(
        context,
        title,
        content,
        thumbnail,
        imageList,
        tagList
    )
}