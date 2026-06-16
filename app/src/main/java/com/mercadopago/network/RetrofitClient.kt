package com.mercadopago.network

import com.mercadopago.services.AuthService
import com.mercadopago.services.PuestoService
import com.mercadopago.services.ServicioService
import com.mercadopago.services.UserService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mercadopago.services.SolicitudService

private val json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
}

object RetrofitClient {

    private const val BASE_URL = "http://192.168.1.102:8080/"
    private val client = OkHttpClient.Builder()
        .cookieJar(CookieManager())
        .addInterceptor(AuthInterceptor())
        .build()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    val auth: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }

    val user: UserService by lazy {
        retrofit.create(UserService::class.java)
    }

    val puesto: PuestoService by lazy {
      retrofit.create(PuestoService::class.java)
    }

    val servicio: ServicioService by lazy {
        retrofit.create(ServicioService::class.java)
    }

    val solicitud: SolicitudService by lazy {
        retrofit.create(SolicitudService::class.java)
    }
}
