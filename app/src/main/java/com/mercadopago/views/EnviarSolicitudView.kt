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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import com.mercadopago.components.BoxInfo
import com.mercadopago.components.ServicioChip
import com.mercadopago.models.PuestoCardModel
import com.mercadopago.network.UIState
import com.mercadopago.viewmodels.PuestoViewModel
import com.mercadopago.viewmodels.SolicitudViewModel

@Composable
fun EnviarSolicitudView(
    navController: NavController,
    puestoId: Int?,
    puestoViewModel: PuestoViewModel = viewModel(),
    solicitudViewModel: SolicitudViewModel = viewModel()
) {
    var mensaje by remember { mutableStateOf("") }
    var validationError by remember { mutableStateOf<String?>(null) }

    val puestoState by puestoViewModel.getPuestoById.collectAsStateWithLifecycle()
    val createSolicitudState by solicitudViewModel.createSolicitudState.collectAsStateWithLifecycle()
    val isSending = createSolicitudState is UIState.Loading

    LaunchedEffect(puestoId) {
        if (puestoId != null) {
            puestoViewModel.getPuestoById(puestoId)
        }
    }

    LaunchedEffect(createSolicitudState) {
        if (createSolicitudState is UIState.Success) {
            navController.navigate("mis-solicitudes") {
                popUpTo("puestos-disponibles")
            }
        }
    }

    Scaffold(
        topBar = {
            Column {
                Spacer(modifier = Modifier.height(40.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(28.dp))

                    Text(
                        text = "Enviar Solicitud",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 2.sp
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.84f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(42.dp))

                when {
                    puestoId == null -> {
                        ErrorSolicitudContent(
                            message = "No se pudo identificar el puesto seleccionado.",
                            onRetry = { navController.popBackStack() }
                        )
                    }

                    puestoState is UIState.Loading -> {
                        CircularProgressIndicator(color = Color(0xFF35C0AB))
                    }

                    puestoState is UIState.Error -> {
                        ErrorSolicitudContent(
                            message = (puestoState as UIState.Error).message,
                            onRetry = { puestoViewModel.getPuestoById(puestoId) }
                        )
                    }

                    puestoState is UIState.Success -> {
                        val puesto = (puestoState as UIState.Success<PuestoCardModel>).data
                        SolicitudFormContent(
                            puesto = puesto,
                            mensaje = mensaje,
                            validationError = validationError,
                            createSolicitudState = createSolicitudState,
                            isSending = isSending,
                            onMensajeChange = {
                                validationError = null
                                mensaje = it
                            },
                            onCancel = { navController.popBackStack() },
                            onSubmit = {
                                val error = validateSolicitudMessage(mensaje)
                                validationError = error
                                if (error == null) {
                                    solicitudViewModel.createSolicitud(
                                        razon = mensaje.trim(),
                                        puestoId = puesto.id
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SolicitudFormContent(
    puesto: PuestoCardModel,
    mensaje: String,
    validationError: String?,
    createSolicitudState: UIState<*>,
    isSending: Boolean,
    onMensajeChange: (String) -> Unit,
    onCancel: () -> Unit,
    onSubmit: () -> Unit
) {
    Text(
        text = "${puesto.codigo} | ${puesto.descripcion}",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace
    )

    Spacer(modifier = Modifier.height(42.dp))

    PuestoSolicitudCard(puesto)

    Spacer(modifier = Modifier.height(48.dp))

    Text(
        text = "MENSAJE PARA LA ADMINISTRACION",
        fontSize = 19.sp,
        fontFamily = FontFamily.Monospace,
        color = Color.Gray,
        fontWeight = FontWeight.Bold,
        letterSpacing = 2.sp
    )

    Spacer(modifier = Modifier.height(18.dp))

    OutlinedTextField(
        value = mensaje,
        onValueChange = onMensajeChange,
        enabled = !isSending,
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp),
        shape = RoundedCornerShape(10.dp),
        placeholder = {
            Text(
                text = "Describe brevemente por que quieres este puesto y tu propuesta...",
                fontSize = 14.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.Gray,
                letterSpacing = 1.sp
            )
        }
    )

    val backendError = (createSolicitudState as? UIState.Error)?.message
    val errorMessage = validationError ?: backendError

    if (errorMessage != null) {
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = errorMessage,
            color = Color(0xFFE12F2F),
            fontSize = 13.sp,
            fontFamily = FontFamily.Monospace
        )
    }

    Spacer(modifier = Modifier.height(36.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = onCancel,
            enabled = !isSending,
            modifier = Modifier
                .width(150.dp)
                .height(44.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD9D9D9),
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Cancelar",
                fontSize = 16.sp,
                fontFamily = FontFamily.Monospace
            )
        }

        Button(
            onClick = onSubmit,
            enabled = !isSending,
            modifier = Modifier
                .width(180.dp)
                .height(44.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF27D3BE),
                contentColor = Color.White
            )
        ) {
            if (isSending) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(18.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Enviar",
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.sp
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(40.dp))
}

@Composable
private fun PuestoSolicitudCard(puesto: PuestoCardModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp, vertical = 26.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = "Puesto",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = puesto.descripcion,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.height(18.dp))
            HorizontalDivider(color = Color.Black)

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BoxInfo(text = puesto.zona)
                BoxInfo(text = "${puesto.areaM2} m²")
            }

            Spacer(modifier = Modifier.height(18.dp))
            HorizontalDivider(color = Color.Black)

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Mensualidad Estimada",
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color.Gray,
                    letterSpacing = 1.sp
                )

                Text(
                    text = "S/${"%.2f".format(puesto.total)}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color.Black)

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "SERVICIOS ASOCIADOS",
                fontSize = 19.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.Gray,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(22.dp))

            Row {
                if (puesto.servicios.isEmpty()) {
                    Text(
                        text = "Sin servicios",
                        color = Color.Gray,
                        fontFamily = FontFamily.Monospace
                    )
                } else {
                    puesto.servicios.take(3).forEachIndexed { index, servicio ->
                        if (index > 0) {
                            Spacer(modifier = Modifier.width(18.dp))
                        }
                        ServicioChip(servicio.nombre)
                    }
                }
            }
        }
    }
}

@Composable
private fun ErrorSolicitudContent(
    message: String,
    onRetry: () -> Unit
) {
    Text(
        text = message,
        color = Color(0xFFE12F2F),
        fontFamily = FontFamily.Monospace
    )

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = onRetry,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF27D3BE)
        )
    ) {
        Text("Volver")
    }
}

private fun validateSolicitudMessage(mensaje: String): String? {
    return when {
        mensaje.isBlank() -> "Ingresa un mensaje para la administracion."
        mensaje.trim().length < 10 -> "El mensaje debe tener al menos 10 caracteres."
        else -> null
    }
}
