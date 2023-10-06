package com.tripbook.tripbook.domain.repository

import com.tripbook.tripbook.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun getLocationList(keyword: String): Flow<List<Location>?>
}