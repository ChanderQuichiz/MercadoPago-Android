package com.mercadopago.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath

        // No enviamos token si es una ruta pública de /auth/
        if (path.contains("/auth/")) {
            return chain.proceed(request)
        }

        val token = SessionManager.accessToken
        val newRequest = request.newBuilder().apply {
            token?.let {
                header("Authorization", "Bearer $it")
            }
        }.build()

        return chain.proceed(newRequest)
    }
}