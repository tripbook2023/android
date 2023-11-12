package com.tripbook.tripbook.domain.usecase

import com.tripbook.tripbook.domain.model.SortType
import com.tripbook.tripbook.domain.repository.ArticleRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetArticleUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    operator fun invoke(keyword: String = "", sortType: SortType = SortType.CREATED_DESC) = repository.getArticles(word = keyword, sortType = sortType)
}