package com.mercadopago.views

import OutlinedSelect
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mercadopago.R
import com.mercadopago.models.CreatePuestoModel
import com.mercadopago.models.PuestoCardModel
import com.mercadopago.models.ServicioFilter
import com.mercadopago.models.ServicioModel
import com.mercadopago.network.UIState
import com.mercadopago.viewmodels.PuestoViewModel
import com.mercadopago.viewmodels.ServicioViewModel

@Composable
fun CrearPuestoView(
    navController: NavController,
    puestosViewModel: PuestoViewModel = viewModel(),
    servicioViewModel: ServicioViewModel = viewModel(),
    updatePuestoModel: Int? = null,
) {
    var puesto by remember { mutableStateOf(PuestoCardModel()) }
    val puestogetbyidState = puestosViewModel.getPuestoById.collectAsStateWithLifecycle()

    LaunchedEffect(updatePuestoModel) {
        if (updatePuestoModel != null) {
            puestosViewModel.getPuestoById(updatePuestoModel)
        }
    }

    LaunchedEffect(puestogetbyidState.value) {
        val state = puestogetbyidState.value
        if (state is UIState.Success) {
            puesto = state.data
        }
    }

    val puestosCreateState = puestosViewModel.createPuesto.collectAsStateWithLifecycle()
    val serviciosState = servicioViewModel.serviciosState.collectAsStateWithLifecycle()
    var showDialogError by remember { mutableStateOf(false) }


    LaunchedEffect(puestosCreateState.value) {
        if (puestosCreateState.value is UIState.Success<*>) {
            navController.popBackStack()
        }
    }

    if (puestogetbyidState.value is UIState.Loading && updatePuestoModel != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color(0XFF35C0AB))
        }
    } else {
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 30.dp)) {
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = if (updatePuestoModel != null) "EDITAR INFORMACION DE PUESTO" else "INFORMACION DE PUESTO",
                fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                modifier = Modifier.padding(vertical = 10.dp)
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedSelect(
                    options = listOf("DISPONIBLE", "OCUPADO"),
                    selectedOption = puesto.estado,
                    modifier = Modifier.fillMaxWidth(0.5f).padding(end = 8.dp),
                    onOptionSelected = { puesto = puesto.copy(estado = it) },
                    label = "Estado"
                )
                OutlinedSelect(
                    options = listOf("Zona A", "Zona B", "Zona C"),
                    selectedOption = puesto.zona,
                    modifier = Modifier.padding(start = 8.dp),
                    onOptionSelected = { puesto = puesto.copy(zona = it) },
                    label = "Zona"
                )
            }

            OutlinedTextField(
                value = puesto.descripcion,
                onValueChange = { puesto = puesto.copy(descripcion = it) },
                placeholder = { Text("Descripcion") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 15.dp)
            )
            
            Row {
                OutlinedTextField(
                    value = if (puesto.areaM2 == 0.0) "" else puesto.areaM2.toString(),
                    onValueChange = { puesto = puesto.copy(areaM2 = it.toDoubleOrNull() ?: 0.0) },
                    modifier = Modifier.fillMaxWidth(0.5f),
                    placeholder = { Text("Area m²") }
                )
                Spacer(modifier = Modifier.width(15.dp))
                OutlinedTextField(
                    value = if (puesto.precioBaseMensual == 0.0) "" else puesto.precioBaseMensual.toString(),
                    onValueChange = { puesto = puesto.copy(precioBaseMensual = it.toDoubleOrNull() ?: 0.0) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Precio m²") }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column {
                Text(
                    "SERVICIO ASIGNADOS",
                    fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                    modifier = Modifier.padding(vertical = 10.dp)
                )

                when (val state = serviciosState.value) {
                    is UIState.Loading -> {
                        CircularProgressIndicator(color = Color(0XFF35C0AB))
                    }
                    is UIState.Error -> {
                        Text("Error al cargar los servicios")
                    }
                    is UIState.Success -> {
                        val servicios: List<ServicioModel> = state.data
                        servicios.forEach { servicio ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .background(Color(0XFFFCFAFA))
                                    .fillMaxWidth()
                                    .border(1.dp, Color(0XFF888787), RoundedCornerShape(5.dp))
                                    .height(40.dp)
                                    .padding(horizontal = 8.dp)
                            ) {
                                Checkbox(
                                    checked = servicio.id in puesto.servicioIds,
                                    onCheckedChange = { checked ->
                                        puesto = puesto.copy(
                                            servicioIds = if (checked) {
                                                puesto.servicioIds + servicio.id
                                            } else {
                                                puesto.servicioIds - servicio.id
                                            }
                                        )
                                    }
                                )
                                Text(
                                    servicio.nombre,
                                    fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                                    color = Color(0XFF777777)
                                )
                                Text(
                                    "$/${servicio.precioMensual} x mes",
                                    fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                                    textAlign = TextAlign.End,
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color(0XFF777777)
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        if (updatePuestoModel != null) {
                            puestosViewModel.updatePuesto(puesto.id, puesto)
                        } else {
                            puestosViewModel.createPuesto(puesto)
                        }
                        navController.navigate("puestos")
                    },
                    colors = ButtonDefaults.buttonColors(Color(0XFF355CC0)),
                    shape = RoundedCornerShape(7.dp)
                ) {
                    Text(
                        if (updatePuestoModel != null) "Actualizar" else "Crear",
                        fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght))
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(Color(0XFFD9D9D9)),
                    shape = RoundedCornerShape(7.dp),
                ) {
                    Text(
                        "Cancelar",
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght))
                    )
                }
            }
        }
    }
}
