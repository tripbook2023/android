package com.tripbook.tripbook.domain.usecase

import com.tripbook.tripbook.domain.model.ArticleDetail
import com.tripbook.tripbook.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleDetailUseCase @Inject constructor(
    private val repository: ArticleRepository
) {

    operator fun invoke(articleId: Long): Flow<ArticleDetail?> = repository.getArticleDetail(articleId)

}