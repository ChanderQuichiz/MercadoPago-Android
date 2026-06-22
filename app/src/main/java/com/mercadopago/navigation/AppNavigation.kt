package com.mercadopago.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mercadopago.viewmodels.UserViewModel
import com.mercadopago.viewmodels.DeudaViewModel
import com.mercadopago.views.CrearPuestoView
import com.mercadopago.views.CrearServicioView
import com.mercadopago.views.LoginAdminView
import com.mercadopago.views.LoginSocioView
import com.mercadopago.views.MiPerfilView
import com.mercadopago.views.PuestosView
import com.mercadopago.views.MisSolicitudesView
import com.mercadopago.views.RegistrarSocioView
import com.mercadopago.views.SociosView
import com.mercadopago.views.MisDeudasView
import com.mercadopago.views.DeudasAdminView
import com.mercadopago.views.EnviarSolicitudView
import com.mercadopago.views.MisPuestosScreen
import com.mercadopago.views.PuestosDisponiblesView
import com.mercadopago.views.ReportesView
import com.mercadopago.views.ServiciosView
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.mercadopago.views.RealizarPagoView
import com.mercadopago.views.PagoExitosoView
import com.mercadopago.views.ContratosView
import com.mercadopago.views.ActivarContratoAdminView
import com.mercadopago.views.ContratoAdminExitosoView
import com.mercadopago.viewmodels.ContratoAdminViewModel
import com.mercadopago.views.SolicitudesPendientesView

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val userViewModel: UserViewModel = viewModel()
    val contratoAdminViewModel: ContratoAdminViewModel = viewModel()
    val deudaViewModel: DeudaViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.LoginSocio.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(Screen.Login.route) {
            LoginAdminView(navController)
        }
        composable(Screen.Puestos.route) {
            PuestosView(navController, userViewModel = userViewModel)
        }
        composable(Screen.MiPerfil.route) {
            MiPerfilView(navController, userViewModel = userViewModel)
        }
        composable(Screen.CrearPuesto.route) {  backStackEntry ->
            val puestoId = backStackEntry.arguments?.getString("puestoId")?.toIntOrNull()
            CrearPuestoView(navController, updatePuestoModel = puestoId)
        }
        composable(Screen.MisSolicitudes.route) {
            MisSolicitudesView(navController, userViewModel = userViewModel)
        }
        composable(Screen.Socios.route) {
            SociosView(navController, userViewModel = userViewModel)
        }
        composable(Screen.Contratos.route) {
            ContratosView(navController, userViewModel = userViewModel)
        }
        composable(Screen.LoginSocio.route) {
            LoginSocioView(navController)
        }
        composable(Screen.RegistroSocio.route) {
            RegistrarSocioView(navController)
        }
        composable(Screen.Deudas.route) {
            DeudasAdminView(navController, userViewModel = userViewModel)
        }
        composable(Screen.MisDeudas.route) {
            MisDeudasView(navController, userViewModel = userViewModel, deudaViewModel = deudaViewModel)
        }
        composable(Screen.PagoExitoso.route) {
            PagoExitosoView(navController)
        }

        composable(
            route = Screen.RealizarPago.route,
            arguments = listOf(navArgument("codigoDeuda") { type = NavType.StringType }),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(300)
                )
            }
        ) { backStackEntry ->
            val codigoDeuda = backStackEntry.arguments?.getString("codigoDeuda") ?: ""
            RealizarPagoView(navController, codigoDeuda = codigoDeuda, deudaViewModel = deudaViewModel)
        }

        composable(Screen.Reportes.route) {
            ReportesView(navController)
        }
        composable(Screen.Servicios.route) {
            ServiciosView(navController, userViewModel = userViewModel)
        }
        composable(Screen.SolicitudesPendientes.route) {
            SolicitudesPendientesView(navController, userViewModel = userViewModel)
        }
        composable(Screen.EnviarSolicitud.route) { backStackEntry ->
            val puestoId = backStackEntry.arguments?.getString("puestoId")?.toIntOrNull()
            EnviarSolicitudView(navController, puestoId = puestoId)
        }
        composable(Screen.PuestosDisponibles.route) {
            PuestosDisponiblesView(navController)
        }
        composable(Screen.CrearServicio.route) { backStackEntry ->
            val servicioId = backStackEntry.arguments?.getString("servicioId")?.toIntOrNull()
            CrearServicioView(navController, updateServicioId = servicioId)
        }

        composable(
            route = Screen.ActivarContratoAdmin.route,
            arguments = listOf(navArgument("codigoSolicitud") { type = NavType.StringType }),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(300)
                )
            }
        ) { backStackEntry ->
            val codigoSolicitud = backStackEntry.arguments?.getString("codigoSolicitud") ?: ""
            ActivarContratoAdminView(navController, codigoSolicitud = codigoSolicitud, contratoViewModel = contratoAdminViewModel)
        }

        composable(Screen.ContratoAdminExitoso.route) {
            ContratoAdminExitosoView(navController)
        }
        composable(Screen.MisPuestos.route) {
            MisPuestosScreen(navController)
        }
    }
}