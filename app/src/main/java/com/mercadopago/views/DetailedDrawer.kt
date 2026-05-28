package com.mercadopago.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Help
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavController
import com.mercadopago.R
import com.mercadopago.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedDrawer(
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit

    ) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState()),

                ) {
                    Spacer(Modifier.height(12.dp))
                    Text("MERCADOPAGO", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge,
                        fontFamily = FontFamily(Font(R.font.changa_medium)),
                        color = Color(0XFF35C0AB),
                        fontWeight = FontWeight.ExtraBold
                        , letterSpacing = 1.sp
                        )
                    HorizontalDivider()


                    NavigationDrawerItem(
                        label = { Text("Reportes") },
                        selected = false,
                        onClick = { /* Handle click */ }
                    )
                    NavigationDrawerItem(
                        label = { Text("Servicios") },
                        selected = false,
                        onClick = { /* Handle click */ }
                    )
                    NavigationDrawerItem(
                        label = {

                                Text("Puestos")


                             },
                        selected = false,
                        onClick = {
                            navController.navigate("puestos")
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Deudas") },
                        selected = false,
                        onClick = { /* Handle click */ }
                    )
                    NavigationDrawerItem(
                        label = { Text("Solicitudes") },
                        selected = false,
                        onClick = { /* Handle click */ }
                    )
                    NavigationDrawerItem(
                        label = { Text("Contratos") },
                        selected = false,
                        onClick = { /* Handle click */ }
                    )
                    NavigationDrawerItem(
                        label = { Text("Socios") },
                        selected = false,
                        onClick = { /* Handle click */ }
                    )


                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    NavigationDrawerItem(
                        label = {
                            Row() {
                                Image(painter = painterResource(id = R.drawable.perfil)
                                    , contentDescription = "",
                                    modifier = Modifier.size(60.dp)
                                )
                                Column() {
                                    Text("Luis alexander",
                                        fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)))
                                    Text("SOCIO",
                                        color = Color(0XFF35C0AB),
                                        fontFamily = FontFamily(Font(R.font.changa_medium))
                                    )
                                }
                            }


                        },
                        selected = false,
                        onClick = {
                            navController.navigate("mi-perfil/1")
                        }
                    )

                    Column(

                        horizontalAlignment = Alignment.CenterHorizontally
                        , modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        NavigationDrawerItem(
                            label = { Text("Cerrar sesion",
                                color = Color(0XFFE12F2F),
                                fontFamily = FontFamily(Font(R.font.sansationbold)),
                                modifier = Modifier.fillMaxWidth()
                                , textAlign = TextAlign.Center
                            ) },
                            selected = false,
                            onClick = { /* Handle click */ },
                            modifier = Modifier.background(Color(0XFFDDDDDD), RoundedCornerShape(30.dp))
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
                    title = { Text("${navController.currentBackStackEntry?.destination?.route?.split("/")[0]}",
                        fontFamily = FontFamily(Font(R.font
                            .changa_medium))
                   , fontSize = 26.sp,
                        fontWeight = FontWeight.Light
                    )},
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
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