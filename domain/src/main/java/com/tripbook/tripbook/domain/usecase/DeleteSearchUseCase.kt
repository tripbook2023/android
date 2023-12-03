package com.tripbook.tripbook.domain.usecase

import com.tripbook.tripbook.domain.repository.SearchRepository
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class DeleteSearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
){
    operator fun invoke(keyword: String) = searchRepository.deleteSearchKeyword(keyword).onCompletion {
        searchRepository.getCurrentSearchHistory()
    }}