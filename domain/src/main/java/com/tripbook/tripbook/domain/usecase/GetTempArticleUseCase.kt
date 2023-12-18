package com.tripbook.tripbook.domain.usecase

import com.tripbook.tripbook.domain.model.TempArticle
import com.tripbook.tripbook.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTempArticleUseCase @Inject constructor(
    private val repository: MemberRepository
){
    operator fun invoke(): Flow<List<TempArticle>?> = repository.getTempArticleList()
}