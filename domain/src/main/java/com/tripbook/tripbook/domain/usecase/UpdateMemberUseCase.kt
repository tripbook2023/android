package com.tripbook.tripbook.domain.usecase

import com.tripbook.tripbook.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class UpdateMemberUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    operator fun invoke(
        name: String?,
        file: File?,
        profile : String?,
        termsOfService: Boolean,
        termsOfPrivacy: Boolean,
        termsOfLocation: Boolean,
        marketingConsent: Boolean,
        gender: String,
        birth: String
    ): Flow<Boolean> = memberRepository.updateMember(
        name, file, profile, termsOfService, termsOfPrivacy,
        termsOfLocation,
        marketingConsent,
        gender,
        birth
    ).also {
        memberRepository.getMember()
    }
}