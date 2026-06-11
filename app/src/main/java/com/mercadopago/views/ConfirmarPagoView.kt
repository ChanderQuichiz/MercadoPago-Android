package com.mercadopago.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.OffsetMapping


data class ServicioDeuda(
    val nombre: String,
    val precio: Double
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmarPagoView(navController: NavController) {

    val servicios = listOf(
        ServicioDeuda("Agua", 45.00),
        ServicioDeuda("Gas", 30.50),
        ServicioDeuda("Electricidad", 89.75)
    )

    val totalServicios = servicios.sumOf { it.precio }

    var numeroTarjeta by remember { mutableStateOf("") }
    var vencimiento by remember { mutableStateOf("") }
    var cvc by remember { mutableStateOf("") }

    val cardVisualTransformation = VisualTransformation { text ->
        val trimmed = if (text.text.length >= 16) text.text.substring(0, 16) else text.text
        var output = ""
        trimmed.forEachIndexed { index, char ->
            output += char
            if ((index + 1) % 4 == 0 && index != 15) output += " "
        }
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 4) return offset
                if (offset <= 8) return offset + 1
                if (offset <= 12) return offset + 2
                return offset + 3
            }
            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 4) return offset
                if (offset <= 9) return offset - 1
                if (offset <= 14) return offset - 2
                return offset - 3
            }
        }
        TransformedText(androidx.compose.ui.text.AnnotatedString(output), offsetMapping)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "CONFIRMAR PAGO",
                        fontFamily = FontFamily(Font(R.font.changa_medium)),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Light
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .padding(8.dp)
                            .background(Color(0xFF35C0AB), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        containerColor = Color.White,
        bottomBar = {
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF35C0AB)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "REALIZAR PAGO",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.changa_medium))
                )
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFB5E8DE), RoundedCornerShape(15.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "DEU-001",
                    fontFamily = FontFamily(Font(R.font.changa_medium)),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "PUE-001",
                    fontFamily = FontFamily(Font(R.font.changa_medium)),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                HorizontalDivider(color = Color(0xFF35C0AB))

                Text(
                    "Servicios a pagar:",
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                servicios.forEach { servicio ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            servicio.nombre,
                            fontFamily = FontFamily(Font(R.font.changa_medium)),
                            fontSize = 14.sp
                        )
                        Text(
                            "S/${servicio.precio}",
                            fontFamily = FontFamily(Font(R.font.changa_medium)),
                            fontSize = 14.sp
                        )
                    }
                }

                HorizontalDivider(color = Color(0xFF35C0AB))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "TOTAL",
                        fontFamily = FontFamily(Font(R.font.changa_medium)),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "S/$totalServicios",
                        fontFamily = FontFamily(Font(R.font.changa_medium)),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF35C0AB)
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    "Número de Tarjeta",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = numeroTarjeta,
                    onValueChange = { input ->
                        val soloNumeros = input.filter { it.isDigit() }.take(16)
                        numeroTarjeta = soloNumeros
                    },
                    visualTransformation = cardVisualTransformation,
                    placeholder = { Text("0000 0000 0000 0000") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF35C0AB),
                        unfocusedBorderColor = Color(0xFFD1D1D1)
                    )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        "Vencimiento",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        value = vencimiento,
                        onValueChange = { input ->
                            val soloNumeros = input.filter { it.isDigit() }.take(4)
                            vencimiento = if (soloNumeros.length >= 3)
                                soloNumeros.substring(0, 2) + "/" + soloNumeros.substring(2)
                            else
                                soloNumeros
                        },
                        placeholder = { Text("MM/AA") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF35C0AB),
                            unfocusedBorderColor = Color(0xFFD1D1D1)
                        )
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        "CVC",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        value = cvc,
                        onValueChange = { if (it.all { c -> c.isDigit() } && it.length <= 3) cvc = it },
                        placeholder = { Text("123") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF35C0AB),
                            unfocusedBorderColor = Color(0xFFD1D1D1)
                        )
                    )
                }
            }
        }
    }
}