package com.tripbook.tripbook.data.mapper

import com.tripbook.libs.network.model.response.ImageResponse
import com.tripbook.tripbook.domain.model.Image

fun ImageResponse.toImage() = Image(
    id = id,
    url = url
)