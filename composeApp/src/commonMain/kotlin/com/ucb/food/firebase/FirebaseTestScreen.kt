package com.ucb.food.firebase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import TodoEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirebaseTestScreen(
    viewModel: FirebaseTestViewModel = koinViewModel()
) {
    var textToSave by remember { mutableStateOf("") }
    val localTodos by viewModel.localTodos.collectAsState()
    val cachedConfig by viewModel.cachedConfig.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Centro de Control",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                StatusCard(
                    title = "Sincronización & Caché",
                    accentColor = Color(0xFF4CAF50)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Valor en Room (Caché):", style = MaterialTheme.typography.labelMedium)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(12.dp)
                        ) {
                            Text(
                                cachedConfig,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                        Text(
                            "Offline Ready: El valor persiste sin internet.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatusCard(
    title: String,
    accentColor: Color,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(accentColor)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            content()
        }
    }
}

@Composable
fun EventItem(todo: TodoEntity, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(start = 12.dp, end = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "•",
                color = Color(0xFF4CAF50),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                todo.title,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        TextButton(onClick = onDelete) {
            Text(
                "Borrar",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}
