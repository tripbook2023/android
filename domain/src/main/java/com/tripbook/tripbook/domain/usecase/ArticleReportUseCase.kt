package com.tripbook.tripbook.domain.usecase

import com.tripbook.tripbook.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleReportUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    operator fun invoke(articleId: Long, content: String): Flow<Boolean> = repository.reportArticle(articleId, content)

}