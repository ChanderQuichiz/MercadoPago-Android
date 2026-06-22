package com.mercadopago.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercadopago.models.ReporteDiarioModel
import com.mercadopago.network.UIState
import com.mercadopago.repositories.ReporteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReporteViewModel : ViewModel() {
    private val reporteRepository = ReporteRepository()

    private val _reporteDiarioState = MutableStateFlow<UIState<ReporteDiarioModel>>(UIState.Idle)
    val reporteDiarioState: StateFlow<UIState<ReporteDiarioModel>> =
        _reporteDiarioState.asStateFlow()

    fun generarReporteDiario(fecha: String) {
        viewModelScope.launch {
            _reporteDiarioState.value = UIState.Loading
            reporteRepository.generarReporteDiario(fecha)
                .onSuccess {
                    _reporteDiarioState.value = UIState.Success(it)
                }
                .onFailure {
                    _reporteDiarioState.value = UIState.Error(it.message ?: "Error desconocido")
                }
        }
    }
}
