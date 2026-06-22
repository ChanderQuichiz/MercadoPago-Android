package com.mercadopago.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.compose.ui.draw.clip
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.automirrored.filled.Assignment

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

    val token by SessionManager.accessTokenFlow.collectAsStateWithLifecycle()
    val userState by userViewModel.meUIState.collectAsStateWithLifecycle()

    LaunchedEffect(token) {
        if (token != null) {
            userViewModel.getMe()
        }
    }

    if (token == null) {
        LaunchedEffect(Unit) {
            navController.navigate("login") {
                popUpTo(0)
            }
        }
        return
    }

    when (val state = userState) {
        is UIState.Idle -> {}
        is UIState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0XFF35C0AB))
            }
        }
        is UIState.Error -> {
            Text(text = "Error cargando perfil...", modifier = Modifier.clickable { userViewModel.getMe() })
        }
        is UIState.Success -> {
            val me = state.data
            val isDarkTheme = isSystemInDarkTheme()
            val profileTextColor = if (isDarkTheme) Color.Black else Color.White

            ModalNavigationDrawer(
                drawerContent = {
                    ModalDrawerSheet {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 20.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .verticalScroll(rememberScrollState())
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
                                Spacer(Modifier.height(12.dp))

                                if (me.role == "ADMIN") {
                                    NavigationDrawerItem(
                                        label = { Text("Reportes") },
                                        selected = false,
                                        onClick = { navController.navigate("reportes") }
                                    )
                                    Spacer(Modifier.height(6.dp))
                                    NavigationDrawerItem(
                                        label = { Text("Solicitudes") },
                                        selected = false,
                                        onClick = { navController.navigate("solicitudes-pendientes") }
                                    )
                                    Spacer(Modifier.height(6.dp))
                                    NavigationDrawerItem(
                                        label = { Text("Servicios") },
                                        selected = false,
                                        onClick = { navController.navigate("servicios") }
                                    )
                                    Spacer(Modifier.height(6.dp))
                                    NavigationDrawerItem(
                                        label = { Text("Puestos") },
                                        selected = false,
                                        onClick = { navController.navigate("puestos") }
                                    )
                                    Spacer(Modifier.height(6.dp))
                                    NavigationDrawerItem(
                                        label = { Text("Deudas") },
                                        selected = false,
                                        onClick = { navController.navigate("deudas") }
                                    )
                                    Spacer(Modifier.height(6.dp))
                                    NavigationDrawerItem(
                                        label = { Text("Socios") },
                                        selected = false,
                                        onClick = { navController.navigate("socios") }
                                    )
                                    Spacer(Modifier.height(6.dp))
                                    NavigationDrawerItem(
                                        label = { Text("Contratos") },

                                        selected = false,
                                        onClick = { navController.navigate("contratos") }
                                    )
                                } else {
                                    NavigationDrawerItem(
                                        label = { Text("Mis Puestos") },
                                        selected = false,
                                        onClick = { navController.navigate("mis-puestos") }
                                    )
                                    Spacer(Modifier.height(6.dp))
                                    NavigationDrawerItem(
                                        label = { Text("Puestos Disponibles") },
                                        icon = {
                                            Icon(
                                                imageVector = Icons.Default.Storefront,
                                                contentDescription = null
                                            )
                                        },
                                        selected = false,
                                        onClick = { navController.navigate("puestos-disponibles") }
                                    )
                                    Spacer(Modifier.height(6.dp))
                                    NavigationDrawerItem(
                                        label = { Text("Mis Solicitudes") },
                                        icon = {
                                            Icon(
                                                imageVector = Icons.Default.Description,
                                                contentDescription = null
                                            )
                                        },
                                        selected = false,
                                        onClick = { navController.navigate("mis-solicitudes") }
                                    )
                                    Spacer(Modifier.height(6.dp))
                                    NavigationDrawerItem(
                                        label = { Text("Mis Deudas") },
                                        icon = {
                                            Icon(
                                                imageVector = Icons.Default.Payments,
                                                contentDescription = null
                                            )
                                        },
                                        selected = false,
                                        onClick = { navController.navigate("mis-deudas") }
                                    )
                                }
                            }

                            HorizontalDivider()

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 12.dp, bottom = 4.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(Color(0XFF35C0AB))
                                        .clickable { navController.navigate("mi-perfil") }
                                        .padding(12.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AccountCircle,
                                        contentDescription = null,
                                        tint = profileTextColor,
                                        modifier = Modifier.size(60.dp)
                                    )
                                    Spacer(Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            me.name,
                                            color = profileTextColor,
                                            fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght))
                                        )
                                        Text(
                                            me.role,
                                            color = profileTextColor,
                                            fontFamily = FontFamily(Font(R.font.changa_medium))
                                        )
                                    }
                                }

                                Spacer(Modifier.height(10.dp))

                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .fillMaxWidth(0.8f)
                                        .clip(RoundedCornerShape(30.dp))
                                        .background(Color(0XFFE12F2F))
                                        .clickable {
                                            authViewModel.sendLogout()
                                            SessionManager.accessToken = null
                                            navController.navigate("login-socio") {
                                                popUpTo(0)
                                            }
                                        }
                                        .height(40.dp)
                                ) {
                                    Text(
                                        "Cerrar sesión",
                                        color = Color.White,
                                        fontFamily = FontFamily(Font(R.font.sansationbold)),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
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
                                    formatRouteTitle(navController.currentBackStackEntry?.destination?.route),
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

fun formatRouteTitle(route: String?): String {
    return route
        ?.split("/")?.get(0)
        ?.split("-")
        ?.joinToString(" ") { word -> word.replaceFirstChar { it.uppercase() } }
        ?: ""
}