package com.tripbook.tripbook.domain.repository

import com.tripbook.tripbook.domain.model.MemberInfo
import com.tripbook.tripbook.domain.model.TempArticle
import kotlinx.coroutines.flow.Flow
import java.io.File

interface MemberRepository {
    fun validateName(
        name: String
    ): Flow<Boolean>

    fun getTempArticleList(): Flow<List<TempArticle>?>
    fun getMember() : Flow<MemberInfo?>

    fun updateMember(
        name: String,
        file: File?,
        profile : String?,
        termsOfService: Boolean,
        termsOfPrivacy: Boolean,
        termsOfLocation: Boolean,
        marketingConsent: Boolean,
        gender: String,
        birth: String
    ): Flow<Boolean>
}