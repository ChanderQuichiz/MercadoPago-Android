package com.mercadopago.navigation
sealed class Screen(val route: String) {

    object Splash : Screen("Splash")
    object Login          : Screen("Login")
    object LoginSocio : Screen("Login Socio")
    object MiPerfil       : Screen("Mi Perfil/{userId}")
    object Puestos        : Screen("Mis Puestos")
    object Deudas : Screen("Mis Deudas")
    object ConfirmarPago : Screen("Confirmar Pago")
    object CrearPuesto    : Screen("Crear Puesto")
    object MisSolicitudes : Screen("Mis Solicitudes")
    object Socios         : Screen("Socios")
}