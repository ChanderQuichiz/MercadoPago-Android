package com.mercadopago.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mercadopago.R
import com.mercadopago.network.SessionManager
import com.mercadopago.network.UIState
import com.mercadopago.viewmodels.AuthViewModel
import com.mercadopago.viewmodels.UserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedDrawer(
    authViewModel: AuthViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel(),
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    
    // Observamos el token de forma reactiva
    val token by SessionManager.accessTokenFlow.collectAsStateWithLifecycle()
    val userState by userViewModel.meUIState.collectAsStateWithLifecycle()

    // Cada vez que el token cambie (ej. tras login), pedimos el perfil
    LaunchedEffect(token) {
        if (token != null && userState !is UIState.Success) {
            userViewModel.getMe()
        }
    }

    // Si no hay token, fuera
    if (token == null) {
        LaunchedEffect(Unit) {
            navController.navigate("login") {
                popUpTo(0)
            }
        }
        return
    }

    when (val state = userState) {
        is UIState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0XFF35C0AB))
            }
        }
        is UIState.Error -> {
            // Si hay error de perfil, reintentamos o salimos
            Text(text = "Error cargando perfil...", modifier = Modifier.clickable { userViewModel.getMe() })
            LaunchedEffect(Unit) {
                // Opcional: podrías poner un delay antes de volver al login
                // navController.navigate("login") { popUpTo(0) }
            }
        }
        is UIState.Success -> {
            val me = state.data
            ModalNavigationDrawer(
                drawerContent = {
                    ModalDrawerSheet {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .verticalScroll(rememberScrollState()),
                        ) {
                            Spacer(Modifier.height(12.dp))
                            Text(
                                "MERCADOPAGO",
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.titleLarge,
                                fontFamily = FontFamily(Font(R.font.changa_medium)),
                                color = Color(0XFF35C0AB),
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.sp
                            )
                            HorizontalDivider()

                            if (me.role == "ADMIN") {
                                NavigationDrawerItem(
                                    label = { Text("Reportes") },
                                    selected = false,
                                    onClick = { navController.navigate("reportes") }
                                )
                                NavigationDrawerItem(
                                    label = { Text("Servicios") },
                                    selected = false,
                                    onClick = { navController.navigate("servicios") }
                                )
                                NavigationDrawerItem(
                                    label = { Text("Puestos") },
                                    selected = false,
                                    onClick = { navController.navigate("puestos") }
                                )
                                NavigationDrawerItem(
                                    label = { Text("Deudas") },
                                    selected = false,
                                    onClick = { navController.navigate("deudas") }
                                )
                                NavigationDrawerItem(
                                    label = { Text("Socios") },
                                    selected = false,
                                    onClick = { navController.navigate("socios") }
                                )
                            } else {
                                NavigationDrawerItem(
                                    label = { Text("Puestos Disponibles") },
                                    selected = false,
                                    onClick = { navController.navigate("puestos-disponibles") }
                                )
                            }

                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                            NavigationDrawerItem(
                                label = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Image(
                                            painter = painterResource(id = R.drawable.perfil),
                                            contentDescription = "",
                                            modifier = Modifier.size(60.dp)
                                        )
                                        Column {
                                            Text(
                                                me.name,
                                                fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght))
                                            )
                                            Text(
                                                me.role,
                                                color = Color(0XFF35C0AB),
                                                fontFamily = FontFamily(Font(R.font.changa_medium))
                                            )
                                        }
                                    }
                                },
                                selected = false,
                                onClick = {
                                    navController.navigate("mi-perfil")
                                }
                            )

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                NavigationDrawerItem(
                                    label = {
                                        Text(
                                            "Cerrar sesion",
                                            color = Color(0XFFE12F2F),
                                            fontFamily = FontFamily(Font(R.font.sansationbold)),
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.Center
                                        )
                                    },
                                    selected = false,
                                    onClick = {
                                        authViewModel.sendLogout()
                                        SessionManager.accessToken = null
                                        navController.navigate("login") {
                                            popUpTo(0)
                                        }
                                    },
                                    modifier = Modifier
                                        .background(
                                            Color(0XFFDDDDDD),
                                            RoundedCornerShape(30.dp)
                                        )
                                        .height(40.dp)
                                        .fillMaxWidth(0.8f)
                                )
                            }
                            Spacer(Modifier.height(12.dp))
                        }
                    }
                },
                drawerState = drawerState
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    navController.currentBackStackEntry?.destination?.route?.split(
                                        "/"
                                    )[0].toString(),
                                    fontFamily = FontFamily(Font(R.font.changa_medium)),
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Light
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    scope.launch {
                                        if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                    }
                                }) {
                                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    content(innerPadding)
                }
            }
        }
    }
}
