package com.mercadopago.services

import com.mercadopago.models.ReporteDiarioModel
import com.mercadopago.models.RequestReporteDiarioModel
import retrofit2.http.Body
import retrofit2.http.POST

interface ReporteService {
    @POST("reportes/reporte-diario")
    suspend fun generarReporteDiario(
        @Body request: RequestReporteDiarioModel
    ): ReporteDiarioModel
}
