package com.mercadopago.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeudasAdminView() {
    var selectedFilter by remember { mutableStateOf("Todas") }
    var searchText by remember { mutableStateOf("") }

    val primaryColor = Color(0xFF00C1A2)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        // Barra superior con el botón estético de las 3 rayas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 44.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {}) {
                Text(text = "☰", fontSize = 26.sp, color = Color.Black)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "Deudas", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }

        // Leyenda explicativa
        Text(
            text = "Una deuda = un periodo mensual por puesto.\nSe generan automáticamente desde contratos activos.",
            fontSize = 13.sp,
            color = Color(0xFF1A1A1A),
            lineHeight = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
        )

        // Sección de filtros y campo de búsqueda activa
        FilterSection(
            selectedFilter = selectedFilter,
            onFilterSelected = { selectedFilter = it },
            searchText = searchText,
            onSearchTextChanged = { searchText = it },
            primaryColor = primaryColor
        )

        // Contenedor con scroll que renderiza directamente el estado vacío
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No se encontraron deudas", color = Color.Gray, fontSize = 14.sp)
            }
        }
    }
}

// Subcomponente reutilizable para los botones de filtro y buscador
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSection(
    selectedFilter: String,
    onFilterSelected: (String) -> Unit,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    primaryColor: Color
) {
    val filters = listOf("Todas", "Pendientes", "Pagadas")
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            filters.forEach { filter ->
                Button(
                    onClick = { onFilterSelected(filter) },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedFilter == filter) primaryColor else Color.White,
                        contentColor = if (selectedFilter == filter) Color.White else primaryColor
                    ),
                    border = BorderStroke(1.dp, primaryColor),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(text = filter, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = onSearchTextChanged,
            placeholder = { Text("Buscar por Codigo Socio...", fontSize = 14.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Lupa", modifier = Modifier.size(20.dp), tint = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = Color(0xFFE0E0E0),
                focusedLabelColor = primaryColor,
                unfocusedLabelColor = Color.Gray
            )
        )
    }
}