package com.mercadopago.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mercadopago.R
import com.mercadopago.models.SolicitudResponseModel
import com.mercadopago.network.UIState
import com.mercadopago.viewmodels.SolicitudViewModel
import com.mercadopago.viewmodels.UserViewModel

@Composable
fun SolicitudesPendientesView(
    navController: NavController,
    userViewModel: UserViewModel,
    solicitudViewModel: SolicitudViewModel = viewModel()
) {
    val context = LocalContext.current
    val pendientesState by solicitudViewModel.pendientesState.collectAsState()
    val updateState by solicitudViewModel.updateState.collectAsState()

    LaunchedEffect(Unit) {
        solicitudViewModel.cargarSolicitudesPendientes()
    }

    LaunchedEffect(updateState) {
        if (updateState is UIState.Success) {
            Toast.makeText(context, "Solicitud procesada correctamente", Toast.LENGTH_SHORT).show()
            solicitudViewModel.clearUpdateState()
        } else if (updateState is UIState.Error) {
            Toast.makeText(context, (updateState as UIState.Error).message, Toast.LENGTH_SHORT).show()
            solicitudViewModel.clearUpdateState()
        }
    }

    DetailedDrawer(navController = navController, userViewModel = userViewModel) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            when (val state = pendientesState) {
                is UIState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0XFF35C0AB))
                    }
                }
                is UIState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Error: ${state.message}",
                            color = Color.Red,
                            modifier = Modifier.clickable { solicitudViewModel.cargarSolicitudesPendientes() }
                        )
                    }
                }
                is UIState.Success -> {
                    val solicitudes = state.data
                    if (solicitudes.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = "No hay solicitudes pendientes",
                                fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                                color = Color.Gray
                            )
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            solicitudes.forEach { solicitud ->
                                PendingSolicitudCard(
                                    solicitud = solicitud,
                                    onAccept = { solicitudViewModel.responderSolicitud(solicitud.codigo, true) },
                                    onReject = { solicitudViewModel.responderSolicitud(solicitud.codigo, false) }
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                    }
                }
                else -> {}
            }
        }
    }
}

@Composable
fun PendingSolicitudCard(
    solicitud: SolicitudResponseModel,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Código: ${solicitud.codigo}",
                        fontFamily = FontFamily(Font(R.font.changa_medium)),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Fecha: ${solicitud.fechaSolicitud}",
                        fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                
                Box(
                    modifier = Modifier
                        .background(Color(0xFFFFB800).copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = solicitud.estado,
                        color = Color(0xFFFFB800),
                        fontFamily = FontFamily(Font(R.font.changa_medium)),
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Razón:",
                fontFamily = FontFamily(Font(R.font.changa_medium)),
                fontSize = 12.sp,
                color = Color.Gray
            )
            Text(
                text = solicitud.razon.ifBlank { "Sin razón especificada" },
                fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Text(
                    text = "Usuario ID: ${solicitud.usuarioId}",
                    fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Puesto ID: ${solicitud.puestoId}",
                    fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onReject,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF3B30)),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Rechazar", fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = onAccept,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF35C0AB)),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Aceptar", fontSize = 12.sp)
                }
            }
        }
    }
}
