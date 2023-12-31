package com.tripbook.tripbook.data.mapper

import com.tripbook.libs.network.model.response.LocationResponse
import com.tripbook.tripbook.domain.model.Location


fun LocationResponse.toLocation() = Location(
    id = id,
    place_name = place_name,
    x = x,
    y = y
)

fun Location.toLocationResponse() = LocationResponse(
    id = id,
    place_name = place_name,
    x = x,
    y = y
)