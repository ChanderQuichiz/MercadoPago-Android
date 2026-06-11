package com.mercadopago.navigation
sealed interface Destino {
    object Login : Destino
    object Registro : Destino
    object Deudas : Destino
    object Perfil : Destino
}