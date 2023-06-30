package com.tripbook.libs.network.interceptor

import com.tripbook.database.Token
import com.tripbook.database.TokenDataStore
import com.tripbook.database.TokenEntity
import com.tripbook.libs.network.service.TokenService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val tokenService: TokenService,
    private val dataStoreManager: TokenDataStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        val token = dataStoreManager.tokenFlow.first()
        token?.let {
            val tokenAddedRequest = chain.request().putAuthorizationHeader(
                Token(it.accessToken, it.refreshToken)
            )

            val firstResponse = chain.proceed(tokenAddedRequest)
            return@runBlocking when (firstResponse.code) {
                401, 500 -> {
                    val refreshedToken = tokenService.refreshToken()
                    val refreshRequest = chain.request().putAuthorizationHeader(
                        Token(refreshedToken.accessToken, refreshedToken.refreshToken))
                    chain.proceed(refreshRequest).also { response ->
                        if (response.isSuccessful) {
                            dataStoreManager.setToken(
                                TokenEntity(
                                    refreshedToken.accessToken,
                                    refreshedToken.refreshToken
                                )
                            )
                        }
                    }
                }
                else -> {
                    firstResponse
                }
            }
        } ?: chain.proceed(chain.request())
    }

    private fun Request.putAuthorizationHeader(token: Token): Request = this.newBuilder()
        .addHeader(AUTHORIZATION, token.accessToken)
        .build()

    companion object {
        private const val AUTHORIZATION = "authorization"
    }

}