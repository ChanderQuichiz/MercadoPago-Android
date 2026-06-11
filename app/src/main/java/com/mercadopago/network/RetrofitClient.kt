package com.mercadopago.network

import com.mercadopago.repositories.AuthRepository
import com.mercadopago.services.AuthService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

private val json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
}

object RetrofitClient {

    private const val BASE_URL =
        "http://10.0.2.2:8080/"

    private val cookieJar =
        CookieManager()


    private val refreshRetrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()

    private val refreshService =
        refreshRetrofit.create(
            AuthService::class.java
        )

    private val authRepository =
        AuthRepository(refreshService)

    private val client =
        OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .addInterceptor(
                AuthInterceptor()
            )
            .authenticator(
                TokenAuthenticator(
                    authRepository
                )
            )
            .build()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()
    }

    val auth: AuthService by lazy {
        retrofit.create(
            AuthService::class.java
        )
    }
}