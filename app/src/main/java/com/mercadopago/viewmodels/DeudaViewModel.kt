package com.mercadopago.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercadopago.models.DeudaDataTableModel
import com.mercadopago.models.MisDeudasModel
import com.mercadopago.network.UIState
import com.mercadopago.repositories.DeudaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DeudaViewModel : ViewModel() {
    private val repository = DeudaRepository()

    private val _deudasState = MutableStateFlow<UIState<List<DeudaDataTableModel>>>(UIState.Idle)
    val deudasState: StateFlow<UIState<List<DeudaDataTableModel>>> = _deudasState.asStateFlow()

    private val _misDeudasState = MutableStateFlow<UIState<List<MisDeudasModel>>>(UIState.Idle)
    val misDeudasState: StateFlow<UIState<List<MisDeudasModel>>> = _misDeudasState.asStateFlow()

    private val _pagoState = MutableStateFlow<UIState<Unit>>(UIState.Idle)
    val pagoState: StateFlow<UIState<Unit>> = _pagoState.asStateFlow()

    init {
        getDeudas()
    }

    fun getDeudas() {
        viewModelScope.launch {
            _deudasState.value = UIState.Loading
            repository.getDeudasDataTable()
                .onSuccess {
                    _deudasState.value = UIState.Success(it)
                }
                .onFailure {
                    _deudasState.value = UIState.Error(it.message ?: "Error desconocido")
                }
        }
    }

    fun getMisDeudas(estado: String? = null) {
        viewModelScope.launch {
            _misDeudasState.value = UIState.Loading
            repository.getMisDeudasDataTable(estado)
                .onSuccess {
                    _misDeudasState.value = UIState.Success(it)
                }
                .onFailure {
                    _misDeudasState.value = UIState.Error(it.message ?: "Error desconocido")
                }
        }
    }

    fun pagarDeuda(codigo: String) {
        viewModelScope.launch {
            _pagoState.value = UIState.Loading
            repository.pagarDeuda(codigo)
                .onSuccess {
                    _pagoState.value = UIState.Success(Unit)
                    getDeudas()
                    getMisDeudas()
                }
                .onFailure {
                    _pagoState.value = UIState.Error(it.message ?: "No se pudo procesar el pago")
                }
        }
    }

    fun resetPagoState() {
        _pagoState.value = UIState.Idle
    }
}