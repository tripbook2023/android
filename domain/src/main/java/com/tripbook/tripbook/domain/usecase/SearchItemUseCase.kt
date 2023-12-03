package com.tripbook.tripbook.domain.usecase

import androidx.paging.PagingData
import com.tripbook.tripbook.domain.model.SortType
import com.tripbook.tripbook.domain.repository.SearchRepository
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class SearchItemUseCase @Inject constructor(
    private val getArticleUseCase: GetArticleUseCase,
    private val searchRepository: SearchRepository,
) {
    operator fun invoke(keyword: String) = getArticleUseCase(keyword = keyword, sortType = SortType.CREATED_ASC).zip(searchRepository.addSearchKeyword(keyword)) { source, addResult ->
        if (addResult) {
            return@zip source
        } else {
            return@zip PagingData.empty()
        }
    }.onCompletion {
        searchRepository.getCurrentSearchHistory()
    }
}