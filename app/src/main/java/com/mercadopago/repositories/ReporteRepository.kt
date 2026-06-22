package com.mercadopago.repositories

import com.mercadopago.models.ReporteDiarioModel
import com.mercadopago.models.RequestReporteDiarioModel
import com.mercadopago.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReporteRepository {
    private val reporte = RetrofitClient.reporte

    suspend fun generarReporteDiario(fecha: String): Result<ReporteDiarioModel> =
        withContext(Dispatchers.IO) {
            try {
                Result.success(
                    reporte.generarReporteDiario(RequestReporteDiarioModel(fecha))
                )
            } catch (e: Exception) {
                Result.failure(Exception(e.message ?: "No se pudo generar el reporte"))
            }
        }
}
