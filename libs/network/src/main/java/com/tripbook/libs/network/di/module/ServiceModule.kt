package com.tripbook.libs.network.di.module

import com.tripbook.libs.network.di.qualifier.ArticleServiceScope
import com.tripbook.libs.network.di.qualifier.AuthServiceScope
import com.tripbook.libs.network.di.qualifier.CommonServiceScope
import com.tripbook.libs.network.di.qualifier.LocationServiceScope
import com.tripbook.libs.network.di.qualifier.MemberNoAuthServiceScope
import com.tripbook.libs.network.di.qualifier.MemberServiceScope
import com.tripbook.libs.network.di.qualifier.TokenServiceScope
import com.tripbook.libs.network.di.qualifier.TripNewsServiceScope
import com.tripbook.libs.network.service.AuthService
import com.tripbook.libs.network.service.CommonService
import com.tripbook.libs.network.service.LocationService
import com.tripbook.libs.network.service.MemberNoAuthService
import com.tripbook.libs.network.service.MemberService
import com.tripbook.libs.network.service.TokenService
import com.tripbook.libs.network.service.TripNewsService
import com.tripbook.libs.network.service.TripArticlesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@Suppress("UNUSED")
object ServiceModule {
    @Provides
    @Singleton
    fun providesMemberService(@MemberServiceScope retrofit: Retrofit)
    : MemberService = retrofit.create()

    @Provides
    @Singleton
    fun providesMemberNoAuthService(@MemberNoAuthServiceScope retrofit: Retrofit)
    : MemberNoAuthService = retrofit.create()

    @Provides
    @Singleton
    fun providesTokenService(@TokenServiceScope retrofit: Retrofit)
    : TokenService = retrofit.create()

    @Provides
    @Singleton
    fun providesAuthService(@AuthServiceScope retrofit: Retrofit)
    : AuthService = retrofit.create()

    @Provides
    @Singleton
    fun providesLocationService(@LocationServiceScope retrofit: Retrofit)
    : LocationService = retrofit.create()
    @Provides
    @Singleton
    fun providesArticleService(@ArticleServiceScope retrofit: Retrofit)
            : TripArticlesService = retrofit.create()

    @Provides
    @Singleton
    fun providesTripNewsService(@TripNewsServiceScope retrofit: Retrofit)
    : TripNewsService = retrofit.create()

    @Provides
    @Singleton
    fun providesCommonService(@CommonServiceScope retrofit: Retrofit)
    : CommonService = retrofit.create()

}