package com.mercadopago.navigation

sealed class Screen(val route: String) {
    object Login    : Screen("login")
    object MiPerfil : Screen("mi-perfil/{userId}")
    object Puestos  : Screen("puestos")
    object CrearPuesto : Screen("crear-puesto")
    object MisSolicitudes : Screen("mis-solicitudes")
    object Socios : Screen("socios")
}