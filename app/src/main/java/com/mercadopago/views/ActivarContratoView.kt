package com.mercadopago.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mercadopago.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivarContratoView(
    navController: NavController,
    codigoSolicitud: String
) {
    var numeroMeses by remember { mutableStateOf(6f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Activar Contrato",
                        fontFamily = FontFamily(Font(R.font.changa_medium)),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Light
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            HorizontalDivider()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "NÚMERO DE MESES",
                    fontFamily = FontFamily(Font(R.font.changa_medium)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "${numeroMeses.toInt()} meses",
                    fontFamily = FontFamily(Font(R.font.changa_medium)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = Color(0xFF35C0AB)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Slider(
                    value = numeroMeses,
                    onValueChange = { numeroMeses = it },
                    valueRange = 1f..24f,
                    steps = 22,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFF35C0AB),
                        activeTrackColor = Color(0xFF35C0AB),
                        inactiveTrackColor = Color(0xFFE0E0E0)
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("1 mes", color = Color.Gray, fontSize = 12.sp)
                    Text("24 meses", color = Color.Gray, fontSize = 12.sp)
                }
            }
        }
    }
}