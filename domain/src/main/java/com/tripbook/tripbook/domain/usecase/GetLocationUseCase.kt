package com.tripbook.tripbook.domain.usecase

import com.tripbook.tripbook.domain.model.Location
import com.tripbook.tripbook.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val repository: LocationRepository
) {

    operator fun invoke(keyword: String): Flow<List<Location>?> {
        return repository.getLocationList(keyword)
    }
}