package com.mercadopago.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercadopago.models.SocioModel
import com.mercadopago.models.UserFilterDto
import com.mercadopago.network.SessionManager
import com.mercadopago.network.UIState
import com.mercadopago.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val userRepository = UserRepository()

    private val _meUIState = MutableStateFlow<UIState<SocioModel>>(UIState.Loading)
    val meUIState: StateFlow<UIState<SocioModel>> = _meUIState.asStateFlow()

    private val _sociosUIState = MutableStateFlow<UIState<List<SocioModel>>>(UIState.Loading)
    val sociosUIState: StateFlow<UIState<List<SocioModel>>> = _sociosUIState.asStateFlow()

    fun getMe() {
        viewModelScope.launch {
            _meUIState.value = UIState.Loading

            userRepository.getMe()
                .onSuccess { user ->
                    _meUIState.value = UIState.Success(user)
                    SessionManager.me = user
                }
                .onFailure { error ->
                    println("Error en getMe: ${error.message}")
                    error.printStackTrace()
                    _meUIState.value = UIState.Error(error.message ?: "Error desconocido")
                }
        }
    }

    fun cargarSocios(
        query: String = "",
        status: String = ""
    ) {
        viewModelScope.launch {
            _sociosUIState.value = UIState.Loading

            val filter = UserFilterDto(
                query = query,
                role = "USER",
                status = status,
                page = 0,
                size = 50
            )

            userRepository.searchUsers(filter)
                .onSuccess { socios ->
                    _sociosUIState.value = UIState.Success(socios)
                }
                .onFailure { error ->
                    println("Error en cargarSocios: ${error.message}")
                    error.printStackTrace()
                    _sociosUIState.value = UIState.Error(error.message ?: "Error al cargar socios")
                }
        }
    }
}