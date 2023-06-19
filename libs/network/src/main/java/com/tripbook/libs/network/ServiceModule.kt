package com.tripbook.libs.network

import com.tripbook.libs.network.interceptor.NoAuthNetworkQualifier
import com.tripbook.libs.network.service.TokenService
import com.tripbook.libs.network.service.UserService
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

object ServiceModule {
    @Provides
    @Singleton
    fun providesUserService(@NoAuthNetworkQualifier retrofit: Retrofit)
    : UserService = retrofit.create()

    @Provides
    @Singleton
    fun providesTokenService(@NoAuthNetworkQualifier retrofit: Retrofit)
    : TokenService = retrofit.create()
}