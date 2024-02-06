package com.tripbook.libs.network.model.response

import com.squareup.moshi.Json

data class UpdateMemberResponse (
    @Json(name = "status")
    val status: String?,
    @Json(name = "message")
    val message: List<String>?
)