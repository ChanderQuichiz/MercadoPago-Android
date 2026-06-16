package com.mercadopago.network

import com.mercadopago.repositories.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
class TokenAuthenticator(
    private val authRepository: AuthRepository
) : Authenticator {

    override fun authenticate(
        route: Route?,
        response: Response
    ): Request? {

        val newToken = runBlocking {
            authRepository.sendRefresh()
        }

        return response.request
            .newBuilder()
            .header(
                "Authorization",
                "Bearer $newToken"
            )
            .build()
    }
}