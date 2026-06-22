package com.mercadopago.repositories

import com.mercadopago.models.ContratoActivoModel
import com.mercadopago.models.ContratoPendienteModel
import com.mercadopago.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContratoRepository {
    private val api = RetrofitClient.contrato

    suspend fun getContratosPendientes(): Result<List<ContratoPendienteModel>> = withContext(Dispatchers.IO) {
        try {
            Result.success(api.getContratosPendientes())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getContratosActivos(): Result<List<ContratoActivoModel>> = withContext(Dispatchers.IO) {
        try {
            Result.success(api.getContratosActivos())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}