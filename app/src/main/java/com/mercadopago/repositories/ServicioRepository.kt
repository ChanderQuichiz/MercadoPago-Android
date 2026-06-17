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

    suspend fun getServicioById(id: Int): Result<ServicioModel> = withContext(Dispatchers.IO) {
        try {
            Result.success(servicio.getServicioById(id))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createServicio(createServicio: ServicioModel): Result<ServicioModel> = withContext(
        Dispatchers.IO
    ) {
        try {
            Result.success(servicio.createServicio(createServicio))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateServicio(id: Int, updateServicio: ServicioModel): Result<ServicioModel> =
        withContext(Dispatchers.IO) {
            try {
                Result.success(servicio.updateServicio(id, updateServicio))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun searchServicio(filter: ServicioFilter): Result<PageResponse<ServicioModel>> = withContext(
        Dispatchers.IO){
        try {
            Result.success( servicio.searchServicio(filter.toQueryMap()))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}

