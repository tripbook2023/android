package com.tripbook.tripbook.domain.usecase

import com.tripbook.tripbook.domain.repository.MemberNoAuthRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val memberNoAuthRepository: MemberNoAuthRepository
) {

    operator fun invoke(
        name: String,
        email: String,
        file: File?,
        termsOfService: Boolean,
        termsOfPrivacy: Boolean,
        termsOfLocation: Boolean,
        marketingConsent: Boolean,
        gender: String,
        birth: String
    ): Flow<Boolean> = memberNoAuthRepository.signUp(
        name, email, file, termsOfService, termsOfPrivacy,
        termsOfLocation,
        marketingConsent,
        gender,
        birth
    )
}