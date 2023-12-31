package com.tripbook.tripbook.data.repository

import com.tripbook.libs.network.NetworkResult
import com.tripbook.libs.network.safeApiCall
import com.tripbook.libs.network.service.LocationService
import com.tripbook.tripbook.data.mapper.toLocation
import com.tripbook.tripbook.domain.model.Location
import com.tripbook.tripbook.domain.repository.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationService: LocationService
) : LocationRepository {

    override fun getLocationList(keyword: String): Flow<List<Location>?> =
        safeApiCall(Dispatchers.IO) {
            locationService.searchLocation("KakaoAK 8a72fb2c49cbce1a512d352c6cb879d0", keyword)
        }.map { response ->
            when(response){
                is NetworkResult.Success -> {
                    response.value.documents.map {
                        it.toLocation()
                    }
                }
                else -> null
            }
        }
}