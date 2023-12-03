package com.tripbook.tripbook.domain.usecase

import com.tripbook.tripbook.domain.repository.SearchRepository
import javax.inject.Inject

class ClearItemUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {

    operator fun invoke() = searchRepository.getCurrentSearchHistory()
}