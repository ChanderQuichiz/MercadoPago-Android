package com.mercadopago.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercadopago.models.ContratoActivoModel
import com.mercadopago.models.ContratoPendienteModel
import com.mercadopago.network.UIState
import com.mercadopago.repositories.ContratoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContratoViewModel : ViewModel() {
    private val repository = ContratoRepository()

    private val _pendientesState = MutableStateFlow<UIState<List<ContratoPendienteModel>>>(UIState.Idle)
    val pendientesState: StateFlow<UIState<List<ContratoPendienteModel>>> = _pendientesState.asStateFlow()

    private val _activosState = MutableStateFlow<UIState<List<ContratoActivoModel>>>(UIState.Idle)
    val activosState: StateFlow<UIState<List<ContratoActivoModel>>> = _activosState.asStateFlow()

    init {
        getPendientes()
        getActivos()
    }

    fun getPendientes() {
        viewModelScope.launch {
            _pendientesState.value = UIState.Loading
            repository.getContratosPendientes()
                .onSuccess { _pendientesState.value = UIState.Success(it) }
                .onFailure { _pendientesState.value = UIState.Error(it.message ?: "Error desconocido") }
        }
    }

    fun getActivos() {
        viewModelScope.launch {
            _activosState.value = UIState.Loading
            repository.getContratosActivos()
                .onSuccess { _activosState.value = UIState.Success(it) }
                .onFailure { _activosState.value = UIState.Error(it.message ?: "Error desconocido") }
        }
    }
}