package com.mercadopago.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercadopago.models.SocioModel
import com.mercadopago.network.SessionManager
import com.mercadopago.network.UIState
import com.mercadopago.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val userRepository = UserRepository()

    private val _meUIState =  MutableStateFlow<UIState<SocioModel>>(UIState.Loading)
    val meUIState: StateFlow<UIState<SocioModel>> = _meUIState.asStateFlow()

    fun getMe(){
        viewModelScope.launch {
            _meUIState.value = UIState.Loading
            userRepository.getMe()
                .onSuccess { user ->
                    _meUIState.value = UIState.Success(user)
                    SessionManager.me = user
                }
                .onFailure { error ->
                    println("Error en getMe: \${error.message}")
                    error.printStackTrace()
                    _meUIState.value = UIState.Error(error.message ?: "Error desconocido")
                }
        }
    }
}
