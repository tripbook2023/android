package com.tripbook.libs.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserAgentNetworkQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthNetworkQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoAuthNetworkQualifier