package com.tripbook.tripbook.data.model

import com.squareup.moshi.Json

data class TermsURL (
    @Json(name = "termsTitle")
    val termsTitle :String,

    @Json(name = "url")
    val url :String
)