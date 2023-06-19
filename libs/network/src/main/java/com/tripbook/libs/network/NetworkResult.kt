package com.tripbook.libs.network

sealed class NetworkResult<out T> {
    data class Success<out T>(val value: T) : NetworkResult<T>()
    data class GeneralError(
        val status: Int? = 0,
        val message: List<String>? = emptyList(),
        val code: String? = null
    ): NetworkResult<Nothing>()
    object NetworkError : NetworkResult<Nothing>()
}