package com.tripbook.tripbook.domain.usecase

import com.tripbook.tripbook.domain.model.LikeArticle
import com.tripbook.tripbook.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleLikeUseCase  @Inject constructor(
    private val repository: ArticleRepository
) {
    operator fun invoke(articleId: Long): Flow<LikeArticle?> = repository.likeArticle(articleId)

}