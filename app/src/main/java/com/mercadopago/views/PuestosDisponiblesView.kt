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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import com.mercadopago.components.PuestoCard
import com.mercadopago.models.PuestoCardModel
import com.mercadopago.network.UIState
import com.mercadopago.viewmodels.PuestoViewModel

@Composable
fun PuestosDisponiblesView(
    navController: NavController,
    puestoViewModel: PuestoViewModel = viewModel()
) {
    val puestosState by puestoViewModel.puestosDisponibles.collectAsStateWithLifecycle()
    var zonaSeleccionada by remember { mutableStateOf("Todas las zonas") }
    var expandedZona by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        puestoViewModel.getPuestosDisponibles()
    }

    DetailedDrawer(navController = navController) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 34.dp, vertical = 32.dp)
        ) {
            Spacer(modifier = Modifier.height(28.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    OutlinedButton(
                        onClick = { expandedZona = true },
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color.Gray),
                        modifier = Modifier
                            .width(226.dp)
                            .height(48.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Black
                        )
                    ) {
                        Text(
                            text = zonaSeleccionada,
                            fontSize = 18.sp
                        )

                        Spacer(modifier = Modifier.width(20.dp))

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Zonas"
                        )
                    }

                    DropdownMenu(
                        expanded = expandedZona,
                        onDismissRequest = { expandedZona = false }
                    ) {
                        buildZonas(puestosState).forEach { zona ->
                            DropdownMenuItem(
                                text = { Text(zona) },
                                onClick = {
                                    zonaSeleccionada = zona
                                    expandedZona = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { puestoViewModel.getPuestosDisponibles() },
                    shape = RoundedCornerShape(7.dp),
                    border = BorderStroke(1.dp, Color.Black),
                    modifier = Modifier
                        .width(125.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF27D3BE),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Actualizar",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            when (val state = puestosState) {
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
                            onClick = { puestoViewModel.getPuestosDisponibles() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF27D3BE)
                            )
                        ) {
                            Text("Reintentar")
                        }
                    }
                }

                is UIState.Success -> {
                    val puestos = filtrarPorZona(state.data, zonaSeleccionada)

                    if (puestos.isEmpty()) {
                        Text(
                            text = "No hay puestos disponibles para mostrar.",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            fontFamily = FontFamily.Monospace,
                            color = Color.Gray
                        )
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(38.dp)
                        ) {
                            items(puestos) { puesto ->
                                PuestoCard(puesto = puesto, navController)
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun buildZonas(state: UIState<List<PuestoCardModel>>): List<String> {
    val zonas = (state as? UIState.Success)
        ?.data
        ?.map { it.zona }
        ?.filter { it.isNotBlank() }
        ?.distinct()
        ?.sorted()
        .orEmpty()

    return listOf("Todas las zonas") + zonas
}

private fun filtrarPorZona(
    puestos: List<PuestoCardModel>,
    zonaSeleccionada: String
): List<PuestoCardModel> {
    return if (zonaSeleccionada == "Todas las zonas") {
        puestos
    } else {
        puestos.filter { it.zona == zonaSeleccionada }
    }
}
