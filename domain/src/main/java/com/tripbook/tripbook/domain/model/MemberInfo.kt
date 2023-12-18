package com.tripbook.tripbook.domain.model

data class MemberInfo(
    val email: String?,
    val name: String,
    val gender: String?,
    val role: String?,
    val birth: String?,
    val profile: String?,
    val termsOfService: Boolean,
    val termsOfPrivacy: Boolean,
    val termsOfLocation: Boolean,
    val marketingConsent: Boolean,
    val point: Long,
    val status: String,
)