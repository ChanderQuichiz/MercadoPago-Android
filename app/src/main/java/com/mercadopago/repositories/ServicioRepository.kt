package com.mercadopago.repositories

import com.mercadopago.mapper.toQueryMap
import com.mercadopago.models.PageResponse
import com.mercadopago.models.ServicioFilter
import com.mercadopago.models.ServicioModel
import com.mercadopago.models.ServicioRequestModel
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

    suspend fun createServicio(createServicio: ServicioModel): Result<Unit> = withContext(
        Dispatchers.IO
    ) {
        try {
            servicio.createServicio(createServicio.toRequest()).close()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception(e.message ?: "No se pudo crear el servicio"))
        }
    }

    suspend fun updateServicio(id: Int, updateServicio: ServicioModel): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                servicio.updateServicio(id, updateServicio.toRequest()).close()
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(Exception(e.message ?: "No se pudo actualizar el servicio"))
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

private fun ServicioModel.toRequest(): ServicioRequestModel {
    return ServicioRequestModel(
        nombre = nombre,
        descripcion = descripcion,
        precioMensual = precioMensual,
        estado = estado
    )
}

