package com.mercadopago.views

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mercadopago.R
import com.mercadopago.network.UIState
import com.mercadopago.viewmodels.DeudaViewModel

class NumeroTarjetaVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length > 16) text.text.substring(0, 16) else text.text
        val formatted = buildString {
            trimmed.forEachIndexed { index, c ->
                if (index != 0 && index % 4 == 0) append(' ')
                append(c)
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val o = offset.coerceIn(0, trimmed.length)
                val spaces = if (o == 0) 0 else (o - 1) / 4
                return (o + spaces).coerceIn(0, formatted.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                val o = offset.coerceIn(0, formatted.length)
                val spaces = if (o == 0) 0 else (o - 1) / 5
                return (o - spaces).coerceIn(0, trimmed.length)
            }
        }

        return TransformedText(AnnotatedString(formatted), offsetMapping)
    }
}

class VencimientoVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length > 4) text.text.substring(0, 4) else text.text
        val formatted = buildString {
            trimmed.forEachIndexed { index, c ->
                if (index == 2) append('/')
                append(c)
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val o = offset.coerceIn(0, trimmed.length)
                return (if (o <= 2) o else o + 1).coerceIn(0, formatted.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                val o = offset.coerceIn(0, formatted.length)
                return (if (o <= 2) o else o - 1).coerceIn(0, trimmed.length)
            }
        }

        return TransformedText(AnnotatedString(formatted), offsetMapping)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RealizarPagoView(
    navController: NavController,
    codigoDeuda: String,
    deudaViewModel: DeudaViewModel
) {
    var numeroTarjeta by remember { mutableStateOf("") }
    var vencimiento by remember { mutableStateOf("") }
    var cvc by remember { mutableStateOf("") }

    var errorNumeroTarjeta by remember { mutableStateOf<String?>(null) }
    var errorVencimiento by remember { mutableStateOf<String?>(null) }
    var errorCvc by remember { mutableStateOf<String?>(null) }

    val isDarkTheme = isSystemInDarkTheme()
    val labelColor = if (isDarkTheme) Color.White else Color.Black

    val misDeudasState by deudaViewModel.misDeudasState.collectAsState()
    val deuda = (misDeudasState as? UIState.Success)?.data?.find { it.codigoDeuda == codigoDeuda }

    val pagoState by deudaViewModel.pagoState.collectAsState()
    var errorPago by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(pagoState) {
        when (val state = pagoState) {
            is UIState.Success -> {
                errorPago = null
                navController.navigate("pago-exitoso") {
                    popUpTo("mis-deudas")
                }
                deudaViewModel.resetPagoState()
            }
            is UIState.Error -> {
                errorPago = state.message
            }
            else -> {}
        }
    }

    val campoColors = OutlinedTextFieldDefaults.colors(
        unfocusedContainerColor = Color(0xFFEDEDED),
        focusedContainerColor = Color(0xFFEDEDED),
        unfocusedBorderColor = Color.Transparent,
        focusedBorderColor = Color(0xFF35C0AB),
        unfocusedTextColor = Color.Black,
        focusedTextColor = Color.Black
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Realizar Pago",
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
                .padding(20.dp)
                .imePadding()
                .verticalScroll(rememberScrollState())
        ) {
            if (deuda == null) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Cargando información de la deuda...", color = Color.Gray)
                }
                return@Column
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFD6F0E8), RoundedCornerShape(18.dp))
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "DEUDA A CANCELAR",
                            fontFamily = FontFamily(Font(R.font.changa_medium)),
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4A4A4A),
                            fontSize = 14.sp
                        )
                        Text(
                            text = deuda.codigoPuesto,
                            fontFamily = FontFamily(Font(R.font.changa_medium)),
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4A4A4A),
                            fontSize = 14.sp
                        )
                    }
                    Text(
                        text = deuda.codigoDeuda,
                        fontFamily = FontFamily(Font(R.font.changa_medium)),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4A4A4A),
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    deuda.servicios.forEach { servicio ->
                        Box(
                            modifier = Modifier
                                .background(Color.White, RoundedCornerShape(50.dp))
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = servicio.uppercase(),
                                fontFamily = FontFamily(Font(R.font.changa_medium)),
                                fontSize = 12.sp,
                                color = Color(0xFF4A4A4A)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                HorizontalDivider(color = Color(0xFF35C0AB))

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "TOTAL:",
                        fontFamily = FontFamily(Font(R.font.changa_medium)),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "${deuda.monto}",
                        fontFamily = FontFamily(Font(R.font.changa_medium)),
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = Color(0xFF35C0AB)
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "NÚMERO DE TARJETA",
                fontFamily = FontFamily(Font(R.font.changa_medium)),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = labelColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = numeroTarjeta,
                onValueChange = { input ->
                    val digits = input.filter { it.isDigit() }
                    numeroTarjeta = if (digits.length > 16) digits.substring(0, 16) else digits
                    errorNumeroTarjeta = null
                },
                placeholder = { Text("0000 0000 0000 0000") },
                singleLine = true,
                maxLines = 1,
                visualTransformation = NumeroTarjetaVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50.dp),
                colors = campoColors
            )

            if (errorNumeroTarjeta != null) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = errorNumeroTarjeta ?: "",
                    color = Color.Red,
                    fontFamily = FontFamily(Font(R.font.changa_medium)),
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "VENCIMIENTO",
                        fontFamily = FontFamily(Font(R.font.changa_medium)),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = labelColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = vencimiento,
                        onValueChange = { input ->
                            val digits = input.filter { it.isDigit() }
                            vencimiento = if (digits.length > 4) digits.substring(0, 4) else digits
                            errorVencimiento = null
                        },
                        placeholder = { Text("MM/AA") },
                        singleLine = true,
                        maxLines = 1,
                        visualTransformation = VencimientoVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(50.dp),
                        colors = campoColors
                    )

                    if (errorVencimiento != null) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = errorVencimiento ?: "",
                            color = Color.Red,
                            fontFamily = FontFamily(Font(R.font.changa_medium)),
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "CVC",
                        fontFamily = FontFamily(Font(R.font.changa_medium)),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = labelColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = cvc,
                        onValueChange = { input ->
                            val digits = input.filter { it.isDigit() }
                            cvc = if (digits.length > 3) digits.substring(0, 3) else digits
                            errorCvc = null
                        },
                        placeholder = { Text("123") },
                        singleLine = true,
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(50.dp),
                        colors = campoColors
                    )

                    if (errorCvc != null) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = errorCvc ?: "",
                            color = Color.Red,
                            fontFamily = FontFamily(Font(R.font.changa_medium)),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (errorPago != null) {
                Text(
                    text = errorPago ?: "",
                    color = Color.Red,
                    fontFamily = FontFamily(Font(R.font.changa_medium)),
                    fontSize = 13.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            Button(
                onClick = {
                    errorNumeroTarjeta = null
                    errorVencimiento = null
                    errorCvc = null

                    val numeroValido = when {
                        numeroTarjeta.isEmpty() -> {
                            errorNumeroTarjeta = "Ingrese los 16 dígitos de su tarjeta, por favor."
                            false
                        }
                        numeroTarjeta.length < 16 -> {
                            errorNumeroTarjeta = "Número de tarjeta invalido."
                            false
                        }
                        else -> true
                    }

                    if (!numeroValido) return@Button

                    val mes = vencimiento.take(2).toIntOrNull()
                    val vencimientoValido = when {
                        vencimiento.isEmpty() -> {
                            errorVencimiento = "Ingrese la fecha de vencimiento."
                            false
                        }
                        vencimiento.length < 4 -> {
                            errorVencimiento = "Fecha invalida."
                            false
                        }
                        mes == null || mes < 1 || mes > 12 -> {
                            errorVencimiento = "Fecha invalida."
                            false
                        }
                        else -> true
                    }

                    if (!vencimientoValido) return@Button

                    val cvcValido = when {
                        cvc.isEmpty() -> {
                            errorCvc = "Ingrese sus 3 dígitos, por favor."
                            false
                        }
                        cvc.length < 3 -> {
                            errorCvc = "Ingrese sus 3 dígitos, por favor."
                            false
                        }
                        else -> true
                    }

                    if (!cvcValido) return@Button

                    deudaViewModel.pagarDeuda(codigoDeuda)
                },
                enabled = pagoState !is UIState.Loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF35C0AB))
            ) {
                if (pagoState is UIState.Loading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(22.dp))
                } else {
                    Text(
                        "Confirmar Pago",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.changa_medium)),
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}