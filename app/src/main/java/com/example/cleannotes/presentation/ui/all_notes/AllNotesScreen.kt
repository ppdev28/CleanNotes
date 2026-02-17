package com.example.cleannotes.presentation.ui.all_notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.StickyNote2
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cleannotes.presentation.ui.home.viewmodel.NotesViewModel
import com.example.cleannotes.presentation.ui.components.BottomNavigationBar
import com.example.cleannotes.presentation.ui.components.SimpleNoteItem

@Composable
fun AllNotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel() // Reutilizamos el mismo VM
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        containerColor = Color(0xFFF8F8F8), // Un gris muy suave de fondo para que resalten las cards blancas
        topBar = {
            // Top Bar Personalizada según la imagen
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // Fila superior: Icono + Clean Notes
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.StickyNote2, // Icono tipo nota/cuadrado
                        contentDescription = "Logo",
                        modifier = Modifier.size(32.dp),
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Clean Notes",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
            }
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            // Título Grande "Notas"
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Notas",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Lista de Notas
            LazyColumn {
                items(state.notes) { note ->
                    SimpleNoteItem(
                        note = note,
                        onClick = {
                            navController.navigate("add_edit_note?noteId=${note.id}")
                        }
                    )
                }
            }
        }
    }
}