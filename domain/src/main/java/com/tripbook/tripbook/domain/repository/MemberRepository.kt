package com.tripbook.tripbook.domain.repository

import kotlinx.coroutines.flow.Flow
import java.io.File

interface MemberRepository {

    fun signUp(
        name: String,
        email: String,
        file: File?,
        termsOfService: Boolean,
        termsOfPrivacy: Boolean,
        termsOfLocation: Boolean,
        marketingConsent: Boolean,
        gender: String,
        birth: String
    ): Flow<Boolean>

    fun validateName(
        name: String
    ): Flow<Boolean>
}