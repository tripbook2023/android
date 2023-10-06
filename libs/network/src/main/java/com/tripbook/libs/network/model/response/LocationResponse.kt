package com.tripbook.libs.network.model.response

data class LocationResponse(
    var documents: List<NetworkLocation>
)

data class NetworkLocation(
    var place_name: String,
    var x: String,
    var y: String
)