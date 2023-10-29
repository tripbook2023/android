package com.tripbook.libs.network.di.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthNetworkQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoAuthNetworkQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoAuthNetworkQualifierNoAgent

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthServiceScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MemberServiceScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TokenServiceScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocationServiceScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TripNewsServiceScope