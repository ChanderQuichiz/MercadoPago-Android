package com.mercadopago.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mercadopago.R
import com.mercadopago.models.MisDeudasModel
import com.mercadopago.network.UIState
import com.mercadopago.viewmodels.DeudaViewModel
import com.mercadopago.viewmodels.UserViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.setValue
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items

@Composable
fun MisDeudasView(
    navController: NavController,
    userViewModel: UserViewModel,
    deudaViewModel: DeudaViewModel
) {
    DetailedDrawer(navController = navController, userViewModel = userViewModel) { padding ->

        var tabState by remember { mutableIntStateOf(0) }

        val tabs = listOf("TODAS", "PENDIENTE", "PAGADA")

        val estadoSeleccionado = when (tabState) {
            1 -> "PENDIENTE"
            2 -> "PAGADA"
            else -> null
        }

        val misDeudasState by deudaViewModel.misDeudasState.collectAsState()

        LaunchedEffect(tabState) {
            deudaViewModel.getMisDeudas(estadoSeleccionado)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF35C0AB))
                    .padding(horizontal = 15.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Historial de deudas y pagos",
                    fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                    color = Color.White,
                    fontSize = 14.sp
                )
            }

            Column(
                modifier = Modifier.padding(15.dp)
            ) {

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(tabs.size) { index ->
                        val seleccionado = tabState == index

                        Box(
                            modifier = Modifier
                                .background(
                                    if (seleccionado) Color(0xFF35C0AB) else Color.White,
                                    RoundedCornerShape(50.dp)
                                )
                                .border(
                                    1.dp,
                                    if (seleccionado) Color(0xFF35C0AB) else Color(0xFFB7B7B7),
                                    RoundedCornerShape(50.dp)
                                )
                                .clickable { tabState = index }
                                .padding(horizontal = 20.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = tabs[index],
                                color = if (seleccionado) Color.White else Color.Black,
                                fontFamily = FontFamily(Font(R.font.changa_medium)),
                                fontSize = 13.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                when (val state = misDeudasState) {
                    is UIState.Idle -> {}

                    is UIState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 30.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color(0xFF35C0AB))
                        }
                    }

                    is UIState.Error -> {
                        Text(
                            text = "Error: ${state.message}",
                            color = Color.Red,
                            fontFamily = FontFamily(Font(R.font.changa_medium))
                        )
                    }

                    is UIState.Success -> {
                        val deudas = state.data

                        if (deudas.isEmpty()) {
                            Text(
                                text = "No hay deudas para mostrar.",
                                color = Color.Gray,
                                fontFamily = FontFamily(Font(R.font.changa_medium))
                            )
                        } else {
                            deudas.forEach { deuda ->
                                DeudaCard(
                                    deuda = deuda,
                                    onPagarClick = {
                                        navController.navigate("realizar-pago/${deuda.codigoDeuda}")
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DeudaCard(
    deuda: MisDeudasModel,
    onPagarClick: () -> Unit
) {
    val esPendiente = deuda.estado.equals("PENDIENTE", ignoreCase = true)
    val colorEstado = if (esPendiente) Color(0xFFFFB800) else Color(0xFF39E75F)
    val colorFondoEstado = colorEstado.copy(alpha = 0.18f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 18.dp)
            .background(Color(0xFFF8F8F8), RoundedCornerShape(18.dp))
            .border(1.dp, Color(0xFFBEBEBE), RoundedCornerShape(18.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = deuda.codigoPuesto,
                    fontFamily = FontFamily(Font(R.font.changa_medium)),
                    color = Color.Black,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${deuda.codigoDeuda}  •  ${deuda.periodo}",
                    color = Color(0xFFA7A7A7),
                    fontFamily = FontFamily(Font(R.font.changa_medium)),
                    fontSize = 11.sp
                )
            }

            Box(
                modifier = Modifier
                    .background(colorFondoEstado, RoundedCornerShape(50.dp))
                    .padding(horizontal = 14.dp, vertical = 5.dp)
            ) {
                Text(
                    text = deuda.estado,
                    color = colorEstado,
                    fontFamily = FontFamily(Font(R.font.changa_medium)),
                    fontSize = 11.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (deuda.servicios.isNotEmpty()) {
            Row {
                deuda.servicios.forEach { servicio ->
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFEDEDED), RoundedCornerShape(5.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = servicio,
                            fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                            fontSize = 10.sp,
                            color = Color(0xFF707070)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "S/ ${deuda.monto}",
                fontFamily = FontFamily(Font(R.font.changa_medium)),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            )

            if (esPendiente) {
                Button(
                    onClick = onPagarClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF35C0AB)),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "Pagar",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}