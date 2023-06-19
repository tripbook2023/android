package com.tripbook.libs.network.di

import com.tripbook.libs.network.interceptor.NoAuthNetworkQualifier
import com.tripbook.libs.network.service.TokenService
import com.tripbook.libs.network.service.MemberService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun providesUserService(@NoAuthNetworkQualifier retrofit: Retrofit)
    : MemberService = retrofit.create()

    @Provides
    @Singleton
    fun providesTokenService(@NoAuthNetworkQualifier retrofit: Retrofit)
    : TokenService = retrofit.create()
}