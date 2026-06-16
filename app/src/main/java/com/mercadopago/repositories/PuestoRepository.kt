package com.mercadopago.repositories

import com.mercadopago.mapper.toQueryMap
import com.mercadopago.models.PageResponse
import com.mercadopago.models.PuestoCardModel
import com.mercadopago.models.PuestoFilterDto
import com.mercadopago.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PuestoRepository {
    private val puesto = RetrofitClient.puesto

    suspend fun createPuesto(createPuesto: PuestoCardModel): Result<PuestoCardModel> = withContext(
        Dispatchers.IO){
        try {
            Result.success( puesto.createPuesto(createPuesto))
        } catch (e: Exception) {
            Result.failure(e)
        }

    }


    suspend fun updatePuesto(id: Int, updatePuesto: PuestoCardModel): Result<PuestoCardModel> = withContext(
        Dispatchers.IO){
        try {
            Result.success( puesto.updatePuesto(id, updatePuesto))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchPuesto(filter: PuestoFilterDto): Result<PageResponse<PuestoCardModel>> = withContext(
        Dispatchers.IO){
        try {
            Result.success( puesto.searchPuesto(filter.toQueryMap()))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPuestoById(id: Int): Result<PuestoCardModel> = withContext(
        Dispatchers.IO){
        try {
            Result.success( puesto.getPuestoById(id))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



}