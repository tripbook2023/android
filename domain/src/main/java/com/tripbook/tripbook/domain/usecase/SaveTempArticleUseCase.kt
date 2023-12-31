package com.tripbook.tripbook.domain.usecase

import com.tripbook.tripbook.domain.model.Location
import com.tripbook.tripbook.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveTempArticleUseCase @Inject constructor(
    private val repository: ArticleRepository
) {

    operator fun invoke(
        tempId: Long?,
        title: String,
        content: String,
        thumbnail: String?,
        imageList: List<Int>?,
        locationList: List<Location>?
    ): Flow<Long?>  = repository.saveTempArticle(
        tempId,
        title,
        content,
        thumbnail,
        imageList,
        locationList
    )
}