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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mercadopago.components.ReporteCard
import com.mercadopago.models.ReporteDiarioModel
import com.mercadopago.models.ReporteModel
import com.mercadopago.network.UIState
import com.mercadopago.viewmodels.ReporteViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ReportesView(
    navController: NavController,
    reporteViewModel: ReporteViewModel = viewModel()
) {
    val reporteState by reporteViewModel.reporteDiarioState.collectAsStateWithLifecycle()
    var fecha by remember { mutableStateOf(todayIsoDate()) }
    var validationError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        reporteViewModel.generarReporteDiario(fecha)
    }
    
    DetailedDrawer(navController = navController) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 26.dp, vertical = 0.dp)
        ) {
            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "REPORTES E INFORME DEL SISTEMA",
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(62.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "REPORTE CAJA DIARIO",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = fecha,
                        onValueChange = {
                            validationError = null
                            fecha = it
                        },
                        placeholder = {
                            Text(
                                text = "yyyy-mm-dd",
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Fecha",
                                modifier = Modifier.size(30.dp)
                            )
                        },
                        modifier = Modifier
                            .width(170.dp)
                            .height(50.dp),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

//                      ----------------------------------------------

                    Button(
                        onClick = {
                            validationError = validateFechaReporte(fecha)
                            if (validationError == null) {
                                reporteViewModel.generarReporteDiario(fecha)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF27D3BE),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(7.dp),
                        modifier = Modifier
                            .width(104.dp)
                            .height(46.dp)
                    ) {
                        Text("Generar")
                    }

//                    ---------------------------------------------------

                }



                TotalReporteCard(reporteState)
            }

            if (validationError != null) {
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = validationError ?: "",
                    color = Color(0xFFE12F2F),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(58.dp))

            when (val state = reporteState) {
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
                            onClick = { reporteViewModel.generarReporteDiario(fecha) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF27D3BE)
                            )
                        ) {
                            Text("Reintentar")
                        }
                    }
                }

                is UIState.Success -> {
                    val reportes = state.data.listadoRecaudacion.map {
                        ReporteModel(
                            codigoPuesto = it.codigoPuesto,
                            nombreSocio = it.emailSocio,
                            hora = formatHoraReporte(it.horaDePago),
                            monto = it.montoRecaudado
                        )
                    }

                    if (reportes.isEmpty()) {
                        Text(
                            text = "No hay recaudaciones para la fecha seleccionada.",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            fontFamily = FontFamily.Monospace,
                            color = Color.Gray
                        )
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(34.dp)
                        ) {
                            items(reportes) { item ->
                                ReporteCard(item = item)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TotalReporteCard(reporteState: UIState<ReporteDiarioModel>) {
    val total = (reporteState as? UIState.Success)?.data?.totalRecaudado ?: 0.0

    Card(
        modifier = Modifier
            .width(150.dp)
            .height(94.dp),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF27D3BE)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "TOTAL",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "S/${"%.2f".format(total)}",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 1.sp
            )
        }
    }
}

private fun todayIsoDate(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
}

private fun validateFechaReporte(fecha: String): String? {
    val regex = Regex("""\d{4}-\d{2}-\d{2}""")
    return if (regex.matches(fecha)) {
        null
    } else {
        "Ingresa la fecha con formato yyyy-MM-dd."
    }
}

private fun formatHoraReporte(hora: String): String {
    return hora.substringBefore(".").takeIf { it.length >= 8 }?.take(8) ?: hora
}
