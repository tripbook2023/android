package com.tripbook.tripbook.data.mapper


import com.tripbook.libs.network.model.response.MemberResponse
import com.tripbook.tripbook.domain.model.MemberInfo

fun MemberResponse.toMemberInfo() = MemberInfo(

    email = email,
    name = name,
    gender = gender,
    role = role,
    birth = birth,
    profile = profile,
    termsOfService = termsOfService,
    termsOfPrivacy = termsOfPrivacy,
    termsOfLocation = termsOfLocation,
    marketingConsent = marketingConsent,
    point = point ?: 0,
    status = status
)