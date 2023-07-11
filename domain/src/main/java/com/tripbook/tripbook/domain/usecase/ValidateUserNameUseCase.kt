package com.tripbook.tripbook.domain.usecase

import com.tripbook.tripbook.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ValidateUserNameUseCase @Inject constructor(
    private val repository: MemberRepository
) {

    operator fun invoke(name: String): Flow<Boolean> = repository.validateName(name)
}