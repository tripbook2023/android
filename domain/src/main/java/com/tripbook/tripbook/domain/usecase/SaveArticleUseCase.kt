package com.tripbook.tripbook.domain.usecase

import com.tripbook.tripbook.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveArticleUseCase @Inject constructor(
    private val repository: ArticleRepository
) {

    operator fun invoke(
        id: Long?,
        title: String,
        content: String,
        thumbnail: String,
        imageList: List<Int>,
        tagList: List<String>?
    ): Flow<Long?> = repository.saveArticle(
        id,
        title,
        content,
        thumbnail,
        imageList,
        tagList
    )
}