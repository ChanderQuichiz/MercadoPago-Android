package com.mercadopago.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mercadopago.views.CrearPuestoView
import com.mercadopago.views.CrearServicioView
import com.mercadopago.views.LoginAdminView
import com.mercadopago.views.LoginSocioView
import com.mercadopago.views.MiPerfilView
import com.mercadopago.views.PuestosView
import com.mercadopago.views.MisSolicitudesView
import com.mercadopago.views.RegistrarSocioView
import com.mercadopago.views.SociosView
import com.mercadopago.views.DeudasAdminView
import com.mercadopago.views.EnviarSolicitudView
import com.mercadopago.views.PuestosDisponiblesView
import com.mercadopago.views.ReportesView
import com.mercadopago.views.ServiciosView

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.LoginSocio.route
    ) {
        composable(Screen.Login.route) {
            LoginAdminView(navController)
        }
        composable(Screen.Puestos.route) {
            PuestosView(navController)
        }
        composable(Screen.MiPerfil.route) {
            MiPerfilView(navController)
        }
        composable(Screen.CrearPuesto.route) {  backStackEntry ->
            val puestoId = backStackEntry.arguments?.getString("puestoId")?.toIntOrNull()
            CrearPuestoView(navController, updatePuestoModel = puestoId)
        }
        composable(Screen.MisSolicitudes.route) {
            MisSolicitudesView(navController)
        }
        composable(Screen.Socios.route) {
            SociosView(navController)
        }
        composable(Screen.LoginSocio.route) {
            LoginSocioView(navController)
        }
        composable(Screen.RegistroSocio.route) {
            RegistrarSocioView(navController)
        }
        composable(Screen.Deudas.route) {
            DeudasAdminView(navController)
        }
        composable(Screen.Reportes.route) {
            ReportesView(navController)
        }
        composable(Screen.Servicios.route) {
            ServiciosView(navController)
        }
        composable(Screen.EnviarSolicitud.route) {
            EnviarSolicitudView(navController)
        }
        composable(Screen.PuestosDisponibles.route) {
            PuestosDisponiblesView(navController)
        }
        composable(Screen.CrearServicio.route) {
            CrearServicioView(navController)
        }
    }
}
