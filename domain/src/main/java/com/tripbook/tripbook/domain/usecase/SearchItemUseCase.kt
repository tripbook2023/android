package com.tripbook.tripbook.domain.usecase

import com.tripbook.tripbook.domain.model.SortType
import com.tripbook.tripbook.domain.repository.ArticleRepository
import com.tripbook.tripbook.domain.repository.SearchRepository
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class SearchItemUseCase @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val searchRepository: SearchRepository,
) {
    operator fun invoke(keyword: String) = articleRepository.getArticles(word = keyword, sortType = SortType.CREATED_ASC).zip(searchRepository.addSearchKeyword(keyword)) { articles, addResult ->
        if (addResult) {
            return@zip articles?.content
        } else {
            return@zip emptyList()
        }
    }.onCompletion {
        searchRepository.getCurrentSearchHistory()
    }
}