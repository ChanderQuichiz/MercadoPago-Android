package com.mercadopago.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mercadopago.network.UIState
import com.mercadopago.viewmodels.AuthViewModel
import com.mercadopago.viewmodels.DeudaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeudasAdminView(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    deudaViewModel: DeudaViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("Todas") }
    val filters = listOf("Todas", "Pendientes", "Pagadas")
    
    val deudasState by deudaViewModel.deudasState.collectAsStateWithLifecycle()

    DetailedDrawer(
        navController = navController
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Una deuda = un periodo mensual por puesto.", fontSize = 14.sp, color = Color.Gray)
            Text("Se generan automáticamente desde contratos activos.", fontSize = 14.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filters.forEach { filter ->
                    FilterChip(
                        selected = (selectedFilter == filter),
                        onClick = { selectedFilter = filter },
                        label = { Text(filter) },
                        enabled = true,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF35C0AB),
                            selectedLabelColor = Color.White
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Buscar por email o código...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(15.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            when (val state = deudasState) {
                is UIState.Idle -> {}
                is UIState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFF35C0AB))
                    }
                }
                is UIState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Error: ${state.message}", color = Color.Red)
                    }
                }
                is UIState.Success -> {
                    val filteredDeudas = state.data.filter { deuda ->
                        val matchesSearch = deuda.emailSocio.contains(searchQuery, ignoreCase = true) || 
                                          deuda.codigoDeuda.contains(searchQuery, ignoreCase = true)
                        
                        val matchesFilter = when (selectedFilter) {
                            "Pendientes" -> deuda.estado.equals("PENDIENTE", ignoreCase = true) || 
                                          deuda.estado.equals("PENDIENTES", ignoreCase = true)
                            "Pagadas" -> deuda.estado.equals("PAGADO", ignoreCase = true) || 
                                       deuda.estado.equals("PAGADA", ignoreCase = true)
                            else -> true
                        }
                        matchesSearch && matchesFilter
                    }

                    if (filteredDeudas.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("No se encontraron deudas", color = Color.Gray)
                        }
                    } else {
                        androidx.compose.foundation.lazy.LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(filteredDeudas.size) { index ->
                                val deuda = filteredDeudas[index]
                                DeudaItem(deuda) {
                                    deudaViewModel.pagarDeuda(deuda.codigoDeuda)
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
fun DeudaItem(deuda: com.mercadopago.models.DeudaDataTableModel, onPayClick: () -> Unit) {
    val isPendiente = deuda.estado.equals("PENDIENTE", ignoreCase = true) || 
                     deuda.estado.equals("PENDIENTES", ignoreCase = true)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = deuda.codigoDeuda, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "Socio: ${deuda.emailSocio}", fontSize = 13.sp, color = Color.Gray)
                Text(text = "Periodo: ${deuda.periodo}", fontSize = 13.sp, color = Color.Gray)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "S/ ${deuda.total}", 
                    fontWeight = FontWeight.Bold, 
                    fontSize = 18.sp, 
                    color = Color(0xFF35C0AB)
                )
                if (isPendiente) {
                    Button(
                        onClick = onPayClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF35C0AB)),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                        modifier = Modifier.height(30.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Pagar", fontSize = 12.sp)
                    }
                } else {
                    Text(
                        text = deuda.estado.uppercase(),
                        color = Color(0xFF4CAF50), 
                        fontWeight = FontWeight.Bold, 
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
