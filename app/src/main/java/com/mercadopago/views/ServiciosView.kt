package com.mercadopago.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mercadopago.components.ServicioCard
import com.mercadopago.models.Paginator
import com.mercadopago.models.ServicioFilter
import com.mercadopago.network.UIState
import com.mercadopago.viewmodels.ServicioViewModel

@Composable
fun ServiciosView(
    navController: NavController,
    servicioViewModel: ServicioViewModel = viewModel()
) {
    val serviciosState by servicioViewModel.serviciosState.collectAsStateWithLifecycle()
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val refreshState = savedStateHandle
        ?.getStateFlow("servicios_refresh", false)
        ?.collectAsStateWithLifecycle()
    val refreshRequested = refreshState?.value ?: false

    var estadoSeleccionado by remember { mutableStateOf("Todos") }
    var expandedEstado by remember { mutableStateOf(false) }
    var mostrarBusqueda by remember { mutableStateOf(false) }
    var textoBusqueda by remember { mutableStateOf("") }
    var queryAplicada by remember { mutableStateOf("") }

    fun buscarServicios(query: String = queryAplicada) {
        servicioViewModel.searchServicios(
            ServicioFilter(
                query = query,
                estado = estadoFiltroServicio(estadoSeleccionado),
                paginator = Paginator(0, 50)
            )
        )
    }

    LaunchedEffect(estadoSeleccionado) {
        buscarServicios()
    }

    LaunchedEffect(refreshRequested) {
        if (refreshRequested) {
            buscarServicios()
            savedStateHandle?.set("servicios_refresh", false)
        }
    }

    DetailedDrawer(navController = navController) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 26.dp, vertical = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
//                Icon(
//                    imageVector = Icons.Default.Menu,
//                    contentDescription = "Menu",
//                    modifier = Modifier.size(34.dp)
//                )
//
//                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = "GESTION SE SERVICIOS FACTURABLES",
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = { mostrarBusqueda = !mostrarBusqueda }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

//            Spacer(modifier = Modifier.height(22.dp))
//
//            Text(
//                text = "Gestion de servicios facturables",
//                fontSize = 16.sp,
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            )

            Spacer(modifier = Modifier.height(42.dp))

            if (mostrarBusqueda) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = textoBusqueda,
                        onValueChange = { textoBusqueda = it },
                        placeholder = { Text("Buscar servicio...") },
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        onClick = {
                            queryAplicada = textoBusqueda.trim()
                            buscarServicios(textoBusqueda.trim())
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF27D3BE),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(7.dp),
                        modifier = Modifier.height(46.dp)
                    ) {
                        Text("Buscar")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            textoBusqueda = ""
                            queryAplicada = ""
                            mostrarBusqueda = false
                            buscarServicios("")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD9D9D9),
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(7.dp),
                        modifier = Modifier.height(46.dp)
                    ) {
                        Text("Cancelar")
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Button(
                        onClick = { expandedEstado = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        border = BorderStroke(1.dp, Color.Gray),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .width(230.dp)
                            .height(46.dp)
                    ) {
                        Text(
                            text = estadoSeleccionado,
                            fontSize = 17.sp
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Estado"
                        )
                    }

                    DropdownMenu(
                        expanded = expandedEstado,
                        onDismissRequest = { expandedEstado = false }
                    ) {
                        listOf("Todos", "Activo", "Inactivo").forEach { estado ->
                            DropdownMenuItem(
                                text = { Text(estado) },
                                onClick = {
                                    estadoSeleccionado = estado
                                    expandedEstado = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        navController.navigate("crear-servicio")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF27D3BE),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(7.dp),
                    border = BorderStroke(1.dp, Color.Black),
                    modifier = Modifier
                        .width(120.dp)
                        .height(46.dp)
                ) {
                    Text(
                        text = "Crear",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(62.dp))

            when (val state = serviciosState) {
                is UIState.Idle -> {
                    // Estado inicial sin contenido visible.
                }

                is UIState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFF35C0AB))
                    }
                }

                is UIState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = state.message,
                            color = Color(0xFFE12F2F),
                            fontFamily = FontFamily.Monospace
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { buscarServicios() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF27D3BE)
                            )
                        ) {
                            Text("Reintentar")
                        }
                    }
                }

                is UIState.Success -> {
                    if (state.data.isEmpty()) {
                        Text(
                            text = "No hay servicios registrados.",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            fontFamily = FontFamily.Monospace,
                            color = Color.Gray
                        )
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(50.dp)
                        ) {
                            items(state.data) { servicio ->
                                ServicioCard(servicio = servicio, navController)
                            }
                        }
                    }
                }

                UIState.Idle -> TODO()
            }
        }
    }
}

private fun estadoFiltroServicio(estado: String): String {
    return when (estado) {
        "Activo" -> "ACTIVO"
        "Inactivo" -> "INACTIVO"
        else -> ""
    }
}
