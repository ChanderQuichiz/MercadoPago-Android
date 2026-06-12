package com.mercadopago.network

import com.mercadopago.models.SocioModel
import kotlinx.coroutines.flow.MutableStateFlow

object SessionManager {
    val accessTokenFlow = MutableStateFlow<String?>(null)
    
    var accessToken: String?
        get() = accessTokenFlow.value
        set(value) {
            accessTokenFlow.value = value
        }

    var me : SocioModel? = null
}
