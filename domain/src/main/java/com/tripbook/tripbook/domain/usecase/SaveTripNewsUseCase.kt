package com.tripbook.tripbook.domain.usecase

import com.tripbook.tripbook.domain.repository.TripNewsRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class SaveTripNewsUseCase @Inject constructor(
    private val repository: TripNewsRepository
) {

    operator fun invoke(
        title: String,
        content: String,
        thumbnail: File,
        imageList: List<File>,
        tagList: List<String>?
    ): Flow<Boolean> = repository.saveTripNews(
        title,
        content,
        thumbnail,
        imageList,
        tagList
    )
}