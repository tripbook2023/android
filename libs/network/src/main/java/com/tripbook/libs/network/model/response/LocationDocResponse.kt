package com.tripbook.libs.network.model.response

import com.squareup.moshi.Json

data class LocationDocResponse(
    var documents: List<LocationResponse>
)

data class LocationResponse(
    var id: Long,
    @Json(name = "name")
    var place_name: String,
    var x: String,
    var y: String
)