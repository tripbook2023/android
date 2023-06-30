package com.tripbook.libs.network.di.module

import com.tripbook.libs.network.di.qualifier.AuthServiceScope
import com.tripbook.libs.network.di.qualifier.MemberServiceScope
import com.tripbook.libs.network.di.qualifier.TokenServiceScope
import com.tripbook.libs.network.service.AuthService
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
    fun providesMemberService(@MemberServiceScope retrofit: Retrofit)
    : MemberService = retrofit.create()

    @Provides
    @Singleton
    fun providesTokenService(@TokenServiceScope retrofit: Retrofit)
    : TokenService = retrofit.create()

    @Provides
    @Singleton
    fun providesAuthService(@AuthServiceScope retrofit: Retrofit)
    : AuthService = retrofit.create()


}