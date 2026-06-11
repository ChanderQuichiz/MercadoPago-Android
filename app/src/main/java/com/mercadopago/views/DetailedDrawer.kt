package com.mercadopago.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle

import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mercadopago.R
import com.mercadopago.navigation.Screen
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedDrawer(
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "MERCADOPAGO",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = FontFamily(Font(R.font.changa_medium)),
                        color = Color(0xFF35C0AB),
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp,
                        fontSize = 32.sp
                    )
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(4.dp))

                    NavigationDrawerItem(
                        label = { Text("Reportes") },
                        selected = false,
                        onClick = { /* Handle click */ }
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    NavigationDrawerItem(
                        label = { Text("Servicios") },
                        selected = false,
                        onClick = { /* Handle click */ }
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    NavigationDrawerItem(
                        label = { Text("Puestos") },
                        selected = currentRoute == Screen.Puestos.route,
                        onClick = { navController.navigate(Screen.Puestos.route) },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Color(0xFF35C0AB),
                            unselectedContainerColor = Color.Transparent,
                            selectedTextColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    NavigationDrawerItem(
                        label = { Text("Deudas") },
                        selected = currentRoute == Screen.Deudas.route,
                        onClick = { navController.navigate(Screen.Deudas.route) },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Color(0xFF35C0AB),
                            unselectedContainerColor = Color.Transparent,
                            selectedTextColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    NavigationDrawerItem(
                        label = { Text("Solicitudes") },
                        selected = currentRoute == Screen.MisSolicitudes.route,
                        onClick = { navController.navigate(Screen.MisSolicitudes.route) },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Color(0xFF35C0AB),
                            unselectedContainerColor = Color.Transparent,
                            selectedTextColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    NavigationDrawerItem(
                        label = { Text("Contratos") },
                        selected = false,
                        onClick = { /* Handle click */ }
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    NavigationDrawerItem(
                        label = { Text("Socios") },
                        selected = currentRoute == Screen.Socios.route,
                        onClick = { navController.navigate(Screen.Socios.route) },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Color(0xFF35C0AB),
                            unselectedContainerColor = Color.Transparent,
                            selectedTextColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    NavigationDrawerItem(
                        label = {
                            Row {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = "Perfil",
                                    modifier = Modifier.size(60.dp),
                                    tint = if (currentRoute == Screen.MiPerfil.route)
                                        Color.White
                                    else
                                        Color.Black
                                )
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxHeight()
                                ) {
                                    Text(
                                        "Luis Alexander",
                                        fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght))
                                    )
                                    Text(
                                        "SOCIO",
                                        color = if (currentRoute == Screen.MiPerfil.route)
                                            Color.White
                                        else
                                            Color(0xFF35C0AB),
                                        fontFamily = FontFamily(Font(R.font.changa_medium))
                                    )
                                }
                            }
                        },
                        selected = currentRoute == Screen.MiPerfil.route,
                        onClick = { navController.navigate("Mi Perfil/1") },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Color(0xFF35C0AB),
                            unselectedContainerColor = Color.Transparent,
                            selectedTextColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(30.dp))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        NavigationDrawerItem(
                            label = {
                                Text(
                                    "Cerrar sesión",
                                    color = Color(0xFFE12F2F),
                                    fontFamily = FontFamily(Font(R.font.sansationbold)),
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            },
                            selected = false,
                            onClick = {
                                navController.navigate(Screen.LoginSocio.route) {
                                popUpTo(0) { inclusive = true }
                                }
                            },
                            modifier = Modifier
                                .background(Color(0xFFDDDDDD), RoundedCornerShape(30.dp))
                                .height(40.dp)
                                .fillMaxWidth(0.8f)
                        )
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
                        val title by remember(currentRoute) {
                            derivedStateOf {
                                val route = currentRoute?.split("/")?.get(0) ?: ""
                                if (route == Screen.ConfirmarPago.route) "Mis Deudas"
                                else route
                            }
                        }
                        Text(
                            title.uppercase(),
                            fontFamily = FontFamily(Font(R.font.changa_medium)),
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Light
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open()
                                else drawerState.close()
                            }
                        }) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menu",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            content(innerPadding)
        }
    }
}