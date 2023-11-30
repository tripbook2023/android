package com.tripbook.auth

import android.content.Context
import android.util.Log
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun Context.loginWithBrowser(account: Auth0, onLoginCompleted: (String) -> Unit) {
    WebAuthProvider.login(account)
        .withScheme("demo")
        .start(this, object : Callback<Credentials, AuthenticationException> {
            override fun onFailure(error: AuthenticationException) {
                error.printStackTrace()
            }

            override fun onSuccess(result: Credentials) {
                val accessToken = result.accessToken
                onLoginCompleted(accessToken).also {
                    Log.d("MyTag", accessToken)
                }
            }
        })
}

@Suppress("UNUSED")
fun Context.logout(account: Auth0) =
    callbackFlow {
        WebAuthProvider.logout(account)
        .withScheme("demo")
        .start(this@logout, callback = object : Callback<Void?, AuthenticationException> {
            override fun onFailure(error: AuthenticationException) {
                error.printStackTrace()
                trySend(false)
            }

            override fun onSuccess(result: Void?) {
                trySend(true)
            }
        })
        awaitClose()
    }