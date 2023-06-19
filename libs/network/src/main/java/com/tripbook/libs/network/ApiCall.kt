package com.tripbook.libs.network

import com.squareup.moshi.Moshi
import com.tripbook.libs.network.model.response.UnitResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): Flow<NetworkResult<T>> =
    flow {
        withContext(dispatcher) {
            try {
                emit(NetworkResult.Success(apiCall.invoke()))
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> emit(NetworkResult.NetworkError)
                    is HttpException -> {
                        val errorResponse = convertErrorBody(throwable)
                        emit(NetworkResult.GeneralError(
                            errorResponse?.status,
                            errorResponse?.message,
                            errorResponse?.code
                        ))
                    }

                    else -> {
                        NetworkResult.GeneralError(null, null, null)
                    }
                }
            }
        }
    }


private fun convertErrorBody(throwable: HttpException): UnitResponse? =
    try {
        throwable.response()?.errorBody()?.source()?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(UnitResponse::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (exception: Exception) {
        null
    }
