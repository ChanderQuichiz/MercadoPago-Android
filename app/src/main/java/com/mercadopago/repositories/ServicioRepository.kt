package com.mercadopago.repositories

import com.mercadopago.mapper.toQueryMap
import com.mercadopago.models.PageResponse
import com.mercadopago.models.ServicioFilter
import com.mercadopago.models.ServicioModel
import com.mercadopago.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ServicioRepository{
    val servicio = RetrofitClient.servicio



    suspend fun searchServicio(filter: ServicioFilter): Result<PageResponse<ServicioModel>> = withContext(
        Dispatchers.IO){
        try {
            Result.success( servicio.searchServicio(filter.toQueryMap()))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}

