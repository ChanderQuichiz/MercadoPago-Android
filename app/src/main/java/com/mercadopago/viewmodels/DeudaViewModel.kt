package com.mercadopago.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercadopago.models.DeudaDataTableModel
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

    fun pagarDeuda(codigo: String) {
        viewModelScope.launch {
            repository.pagarDeuda(codigo)
                .onSuccess {
                    getDeudas() // Recargar lista tras pago
                }
                .onFailure {
                    // Manejar error si es necesario
                }
        }
    }
}
