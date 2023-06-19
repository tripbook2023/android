package com.tripbook.tripbook.domain.repository

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface MemberRepository {

    fun signUp(
        name: String,
        email: String,
        fileUri: Uri,
        termsOfService: Boolean,
        termsOfPrivacy: Boolean,
        termsOfLocation: Boolean,
        marketingConsent: Boolean,
        gender: String,
        birth: String
    ): Flow<Boolean>
}