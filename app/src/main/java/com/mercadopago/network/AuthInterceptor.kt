package com.mercadopago.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(
        chain: Interceptor.Chain
    ): Response {

        val token = SessionManager.accessToken

        val request = chain.request()
            .newBuilder()
            .apply {
                token?.let {
                    header(
                        "Authorization",
                        "Bearer $it"
                    )
                }
            }
            .build()

        return chain.proceed(request)
    }
}