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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mercadopago.viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeudasAdminView(
navController: NavController
,authViewModel: AuthViewModel = AuthViewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("Todas") }
    val filters = listOf("Todas", "Pendientes", "Pagadas")

    DetailedDrawer(
        navController = navController
    ) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(50.dp))


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
            placeholder = { Text("Buscar por Codigo Socio...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedBorderColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(50.dp))

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No se encontraron deudas", color = Color.Gray)
        }
    }}
}