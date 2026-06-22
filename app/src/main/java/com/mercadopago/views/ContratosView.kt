package com.mercadopago.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.clip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.mercadopago.models.ContratoActivoModel
import com.mercadopago.models.ContratoPendienteModel
import com.mercadopago.network.UIState
import com.mercadopago.viewmodels.ContratoViewModel
import com.mercadopago.viewmodels.UserViewModel

@Composable
fun ContratosView(
    navController: NavController,
    userViewModel: UserViewModel,
    contratoViewModel: ContratoViewModel = viewModel()
) {
    DetailedDrawer(navController = navController, userViewModel = userViewModel) { padding ->

        var tabState by remember { mutableIntStateOf(0) }

        val pendientesState by contratoViewModel.pendientesState.collectAsState()
        val activosState by contratoViewModel.activosState.collectAsState()

        val cantidadPendientes = (pendientesState as? UIState.Success)?.data?.size ?: 0

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF35C0AB))
                    .padding(horizontal = 15.dp, vertical = 14.dp)
            ) {
                Text(
                    text = "Activa contratos desde las solicitudes aceptadas y gestiona los vigentes.",
                    fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                    color = Color.White,
                    fontSize = 13.sp
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFB7B7B7), RoundedCornerShape(50.dp))
                        .clip(RoundedCornerShape(50.dp))
                ) {
                    val seleccionadoPendientes = tabState == 0
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .weight(1f)
                            .background(if (seleccionadoPendientes) Color(0xFF35C0AB) else Color.White)
                            .clickable { tabState = 0 }
                            .padding(vertical = 10.dp)
                    ) {
                        Text(
                            text = "Sin Contratos",
                            color = if (seleccionadoPendientes) Color.White else Color.Black,
                            fontFamily = FontFamily(Font(R.font.changa_medium)),
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(22.dp)
                                .background(Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$cantidadPendientes",
                                color = Color(0xFF35C0AB),
                                fontFamily = FontFamily(Font(R.font.changa_medium)),
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                lineHeight = 11.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    val seleccionadoActivos = tabState == 1
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .background(if (seleccionadoActivos) Color(0xFF35C0AB) else Color.White)
                            .clickable { tabState = 1 }
                            .padding(vertical = 10.dp)
                    ) {
                        Text(
                            text = "Contratos Activos",
                            color = if (seleccionadoActivos) Color.White else Color.Black,
                            fontFamily = FontFamily(Font(R.font.changa_medium)),
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                if (tabState == 0) {
                    when (val state = pendientesState) {
                        is UIState.Idle -> {}
                        is UIState.Loading -> {
                            Box(modifier = Modifier.fillMaxWidth().padding(top = 30.dp), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(color = Color(0xFF35C0AB))
                            }
                        }
                        is UIState.Error -> {
                            Text("Error: ${state.message}", color = Color.Red, fontFamily = FontFamily(Font(R.font.changa_medium)))
                        }
                        is UIState.Success -> {
                            if (state.data.isEmpty()) {
                                Text("No hay solicitudes pendientes de contrato.", color = Color.Gray, fontFamily = FontFamily(Font(R.font.changa_medium)))
                            } else {
                                state.data.forEach { pendiente ->
                                    ContratoPendienteCard(pendiente = pendiente, onActivarClick = {
                                        navController.navigate("activar-contrato-admin/${pendiente.codigoSolicitud}")
                                    })
                                }
                            }
                        }
                    }
                } else {
                    when (val state = activosState) {
                        is UIState.Idle -> {}
                        is UIState.Loading -> {
                            Box(modifier = Modifier.fillMaxWidth().padding(top = 30.dp), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(color = Color(0xFF35C0AB))
                            }
                        }
                        is UIState.Error -> {
                            Text("Error: ${state.message}", color = Color.Red, fontFamily = FontFamily(Font(R.font.changa_medium)))
                        }
                        is UIState.Success -> {
                            if (state.data.isEmpty()) {
                                Text("No hay contratos activos.", color = Color.Gray, fontFamily = FontFamily(Font(R.font.changa_medium)))
                            } else {
                                state.data.forEach { activo ->
                                    ContratoActivoCard(activo = activo)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ContratoPendienteCard(
    pendiente: ContratoPendienteModel,
    onActivarClick: () -> Unit
) {
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
            Text(
                text = pendiente.codigoSolicitud,
                fontFamily = FontFamily(Font(R.font.changa_medium)),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black
            )
            Box(
                modifier = Modifier
                    .background(Color(0xFFFFB800).copy(alpha = 0.2f), RoundedCornerShape(50.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Sin Contrato",
                    color = Color(0xFFB97F00),
                    fontFamily = FontFamily(Font(R.font.changa_medium)),
                    fontSize = 11.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = pendiente.nombreSocio,
            fontFamily = FontFamily(Font(R.font.changa_medium)),
            fontSize = 19.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${pendiente.dniSocio} · ${pendiente.emailSocio} · ${pendiente.phoneSocio}",
            fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            color = Color(0xFF707070)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${pendiente.codigoPuesto} · ${pendiente.descripcionPuesto}",
            fontFamily = FontFamily(Font(R.font.changa_medium)),
            fontSize = 13.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${pendiente.zonaPuesto} · ${pendiente.servicios.joinToString(" · ")}",
            fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            color = Color(0xFF707070)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "TOTAL MENSUAL: S/ ${pendiente.montoMensual}",
            fontFamily = FontFamily(Font(R.font.changa_medium)),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onActivarClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF35C0AB)),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                "Activar Contrato",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.changa_medium))
            )
        }
    }
}

@Composable
fun ContratoActivoCard(
    activo: ContratoActivoModel
) {
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
            Text(
                text = activo.codigo,
                fontFamily = FontFamily(Font(R.font.changa_medium)),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black
            )
            Box(
                modifier = Modifier
                    .background(Color(0xFF39E75F).copy(alpha = 0.2f), RoundedCornerShape(50.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = activo.estado,
                    color = Color(0xFF1E9E40),
                    fontFamily = FontFamily(Font(R.font.changa_medium)),
                    fontSize = 11.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = activo.email,
            fontFamily = FontFamily(Font(R.font.changa_medium)),
            fontSize = 14.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${activo.codigoPuesto} · ${activo.fechaInicio} a ${activo.fechaFin}",
            fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
            fontSize = 11.sp,
            color = Color(0xFF707070)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "TOTAL MENSUAL: S/ ${activo.montoMensual}",
            fontFamily = FontFamily(Font(R.font.changa_medium)),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = Color.Black
        )
    }
}