package com.tripbook.tripbook.domain.usecase

import com.tripbook.tripbook.domain.repository.TripNewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveArticleUseCase @Inject constructor(
    private val repository: TripNewsRepository
) {

    operator fun invoke(
        id: Long?,
        title: String,
        content: String,
        thumbnail: String,
        imageList: List<Int>,
        tagList: List<String>?
    ): Flow<Boolean> = repository.saveTripNews(
        id,
        title,
        content,
        thumbnail,
        imageList,
        tagList
    )
}