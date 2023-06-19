package com.tripbook.libs.network

import com.squareup.moshi.Moshi
import com.tripbook.libs.network.model.response.ErrorResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> Unit) {
    return withContext(dispatcher) {
        try {
            NetworkResult.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> NetworkResult.NetworkError
                is HttpException -> {
                    val errorResponse = convertErrorBody(throwable)
                    NetworkResult.GeneralError(
                        errorResponse?.status,
                        errorResponse?.message,
                        errorResponse?.code
                    )
                }
                else -> {
                    NetworkResult.GeneralError(null, null, null)
                }
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): ErrorResponse? { // ERROR_RESPONSE
    return try {
        throwable.response()?.errorBody()?.source()?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (exception: Exception) {
        null
    }
}