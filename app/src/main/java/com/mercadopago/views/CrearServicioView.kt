package com.mercadopago.views

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mercadopago.models.ServicioModel
import com.mercadopago.network.UIState
import com.mercadopago.viewmodels.ServicioViewModel

@Composable
fun CrearServicioView(
    navController: NavController,
    servicioViewModel: ServicioViewModel = viewModel(),
    updateServicioId: Int? = null
) {
    var servicio by remember { mutableStateOf(ServicioModel(0, "", "", 0.0, "ACTIVO")) }
    var precioMensual by remember { mutableStateOf("") }
    var validationError by remember { mutableStateOf<String?>(null) }

    val getServicioState by servicioViewModel.getServicioByIdState.collectAsStateWithLifecycle()
    val createServicioState by servicioViewModel.createServicioState.collectAsStateWithLifecycle()
    val updateServicioState by servicioViewModel.updateServicioState.collectAsStateWithLifecycle()

    val saveState = if (updateServicioId != null) updateServicioState else createServicioState
    val isSaving = saveState is UIState.Loading

    LaunchedEffect(updateServicioId) {
        if (updateServicioId != null) {
            servicioViewModel.getServicioById(updateServicioId)
        }
    }

    LaunchedEffect(getServicioState) {
        val state = getServicioState
        if (state is UIState.Success) {
            servicio = state.data
            precioMensual = state.data.precioMensual.toString()
        }
    }

    LaunchedEffect(createServicioState, updateServicioState) {
        if (createServicioState is UIState.Success || updateServicioState is UIState.Success) {
            navController.popBackStack()
        }
    }

    if (updateServicioId != null && getServicioState is UIState.Loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFF35C0AB))
        }
        return
    }

    Scaffold(
        topBar = {
            Column {
                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp)
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            Spacer(modifier = Modifier.height(34.dp))

            Text(
                text = if (updateServicioId != null) "EDITAR SERVICIO" else "CREAR SERVICIO",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(34.dp))

            CampoTextoServicio(
                titulo = "NOMBRE DEL SERVICIO",
                valor = servicio.nombre,
                placeholder = "Agua, Gas, Electricidad...",
                enabled = !isSaving,
                onValueChange = {
                    validationError = null
                    servicio = servicio.copy(nombre = it)
                }
            )

            Spacer(modifier = Modifier.height(34.dp))

            CampoTextoServicio(
                titulo = "DESCRIPCION",
                valor = servicio.descripcion,
                placeholder = "Descripcion del servicio...",
                enabled = !isSaving,
                onValueChange = {
                    validationError = null
                    servicio = servicio.copy(descripcion = it)
                }
            )

            Spacer(modifier = Modifier.height(34.dp))

            CampoTextoServicio(
                titulo = "PRECIO MENSUAL",
                valor = precioMensual,
                placeholder = "0.00",
                enabled = !isSaving,
                onValueChange = {
                    validationError = null
                    precioMensual = it
                }
            )

            Spacer(modifier = Modifier.height(34.dp))

            Text(
                text = "ESTADO",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(8.dp))

            EstadoField(
                estado = servicio.estado,
                enabled = !isSaving,
                onEstadoChange = {
                    validationError = null
                    servicio = servicio.copy(estado = it)
                }
            )

            val errorMessage = validationError
                ?: (saveState as? UIState.Error)?.message
                ?: (getServicioState as? UIState.Error)?.message

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = errorMessage,
                    color = Color(0xFFE12F2F),
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        val precio = precioMensual.toDoubleOrNull()
                        validationError = validateServicio(servicio, precio)

                        if (validationError == null && precio != null) {
                            val request = servicio.copy(precioMensual = precio)
                            if (updateServicioId != null) {
                                servicioViewModel.updateServicio(updateServicioId, request)
                            } else {
                                servicioViewModel.createServicio(request)
                            }
                        }
                    },
                    enabled = !isSaving,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF27D3BE)),
                    shape = MaterialTheme.shapes.small
                ) {
                    if (isSaving) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier
                                .height(18.dp)
                                .width(18.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = if (updateServicioId != null) "Actualizar" else "Crear",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                Button(
                    onClick = { navController.popBackStack() },
                    enabled = !isSaving,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD9D9D9),
                        contentColor = Color.Black
                    ),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = "Cancelar",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}

private fun validateServicio(servicio: ServicioModel, precio: Double?): String? {
    return when {
        servicio.nombre.isBlank() -> "Ingresa el nombre del servicio."
        servicio.descripcion.isBlank() -> "Ingresa la descripcion del servicio."
        precio == null -> "Ingresa un precio mensual valido."
        precio <= 0.0 -> "El precio mensual debe ser mayor a 0."
        servicio.estado.isBlank() -> "Selecciona el estado del servicio."
        else -> null
    }
}

@Composable
fun CampoTextoServicio(
    titulo: String,
    valor: String,
    placeholder: String,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit
) {
    Text(
        text = titulo,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = valor,
        onValueChange = onValueChange,
        enabled = enabled,
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 1.5.sp,
                color = Color.DarkGray
            )
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        singleLine = true
    )
}

@Composable
fun EstadoField(
    estado: String,
    enabled: Boolean = true,
    onEstadoChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = estado,
            onValueChange = {},
            enabled = enabled,
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            textStyle = LocalTextStyle.current.copy(
                fontFamily = FontFamily.Monospace,
                letterSpacing = 1.5.sp,
                color = DarkGray
            ),
            trailingIcon = {
                IconButton(
                    onClick = { expanded = true },
                    enabled = enabled
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Seleccionar estado"
                    )
                }
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("ACTIVO") },
                onClick = {
                    onEstadoChange("ACTIVO")
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = { Text("INACTIVO") },
                onClick = {
                    onEstadoChange("INACTIVO")
                    expanded = false
                }
            )
        }
    }
}
