package com.mercadopago.navigation

sealed class Screen(val route: String) {
    object Login    : Screen("login")
    object MiPerfil : Screen("mi-perfil")
    object Puestos  : Screen("puestos")
    object CrearPuesto : Screen("crear-puesto?puestoId={puestoId}")
    object MisSolicitudes : Screen("mis-solicitudes")
    object Socios : Screen("socios")
    object LoginSocio : Screen("login-socio")
    object RegistroSocio: Screen("registro-socio")


    object Deudas: Screen("deudas")
    object Reportes: Screen("reportes")
    object Servicios: Screen("servicios")
    object SolicitudesPendientes: Screen("solicitudes-pendientes")
    object CrearServicio: Screen("crear-servicio?servicioId={servicioId}")
    object Contratos: Screen("contratos")


    object EnviarSolicitud: Screen("enviar-solicitud?puestoId={puestoId}")
    object PuestosDisponibles: Screen("puestos-disponibles")
    object MisDeudas: Screen("mis-deudas")
    object RealizarPago: Screen("realizar-pago/{codigoDeuda}")
    object PagoExitoso: Screen("pago-exitoso")
    object MisPuestos: Screen("mis-puestos")
}
