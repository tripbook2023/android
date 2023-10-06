package com.tripbook.tripbook.data.mapper

import com.tripbook.libs.network.model.response.NetworkLocation
import com.tripbook.tripbook.domain.model.Location


fun NetworkLocation.toDomainLocation() = Location(
    place_name = place_name,
    x = x,
    y = y
)