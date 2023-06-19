package com.tripbook.libs.network.interceptor

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthNetworkQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoAuthNetworkQualifier