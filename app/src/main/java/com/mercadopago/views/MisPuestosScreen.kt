package com.mercadopago.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mercadopago.components.MisPuestoCard
import com.mercadopago.network.UIState
import com.mercadopago.viewmodels.PuestoViewModel

@Composable
fun MisPuestosScreen(
    navController: NavController,
    puestosViewModel: PuestoViewModel = viewModel()
) {
    val misPuestosState by puestosViewModel.misPuestos.collectAsStateWithLifecycle()

    // Disparar la carga de datos al entrar
    LaunchedEffect(Unit) {
        puestosViewModel.getMisPuestos()
    }

    DetailedDrawer(navController = navController) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val state = misPuestosState) {
                is UIState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0XFF35C0AB))
                    }
                }

                is UIState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = state.message)
                        Button(onClick = { puestosViewModel.getMisPuestos() }) {
                            Text("Reintentar")
                        }
                    }
                }

                is UIState.Success -> {
                    val puestos = state.data
                    if (puestos.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("No tienes puestos asignados.")
                        }
                    } else {
                        Column(modifier = Modifier.padding(10.dp)) {
                            puestos.forEach { puesto ->
                                MisPuestoCard(puesto)
                            }
                        }
                    }
                }
                else -> {}
            }
        }
    }
}
