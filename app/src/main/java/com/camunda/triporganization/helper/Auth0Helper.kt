package com.camunda.triporganization.helper

import android.content.Context
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.jwt.JWT
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.camunda.triporganization.model.UserRole

object Auth0Helper {

    private val account = Auth0(
        clientId = "WrUYvufrcXOFjib80lKFSzKMGPMFq2gc",
        domain = "dev-ow7jzpqrkfnjnz41.us.auth0.com"
    )

    fun logIn(context: Context, onLoggedIn: (String) -> Unit) {
        WebAuthProvider.login(account)
            .withScheme("demo")
            .withScope("openid profile email")
            .start(context, object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(exception: AuthenticationException) = Unit

                override fun onSuccess(credentials: Credentials) {
                    val jwt = JWT(credentials.idToken)
                    val userId = jwt.subject
                    val roles = jwt.getClaim("https://automatictrips.app/roles")
                        .asList(String::class.java)

                    if (roles.map { it.lowercase() }.contains("trip coordinator")) {
                        AppSingleton.userId = userId
                        AppSingleton.userRole = UserRole.COORDINATOR
                    } else {
                        AppSingleton.userId = userId
                        AppSingleton.userRole = UserRole.GUIDE
                    }
                    onLoggedIn(credentials.user.email ?: "")
                }
            })
    }

    fun logOut(context: Context, onLoggedOut: () -> Unit) {
        WebAuthProvider.logout(account)
            .withScheme("demo")
            .start(context, object : Callback<Void?, AuthenticationException> {
                override fun onFailure(exception: AuthenticationException) = Unit

                override fun onSuccess(payload: Void?) {
                    AppSingleton.userId = null
                    AppSingleton.userRole = null
                    onLoggedOut()
                }
            })
    }
}