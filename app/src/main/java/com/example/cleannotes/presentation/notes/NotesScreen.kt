package com.example.cleannotes.presentation.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cleannotes.presentation.notes.components.NoteItem

@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        containerColor = Color.White, // Fondo blanco puro como en la imagen
        floatingActionButton = {
            // Fila de botones flotantes inferiores
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Botón izquierdo (Menú / Notas)
                FloatingActionButton(
                    onClick = { /* Acción futura */ },
                    containerColor = Color(0xFF1F1F1F), // Negro casi puro
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Menu")
                }

                // Botón derecho (Notificaciones / Añadir)
                // Lo usaremos como el botón de AÑADIR NOTA por ahora para que sea funcional
                FloatingActionButton(
                    onClick = { navController.navigate("add_edit_note") }, // Navegar a crear
                    containerColor = Color(0xFF1F1F1F),
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // --- CABECERA ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 24.dp, end = 24.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Título grande
                Text(
                    text = "Notas de hoy",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                // Imagen de perfil (Placeholder)
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Gray, CircleShape)
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = Color.White
                    )
                }
            }

            // --- LISTA DE NOTAS ---
            LazyColumn(
                contentPadding = PaddingValues(bottom = 100.dp) // Espacio para los botones flotantes
            ) {
                items(state.notes) { note ->
                    // Lógica visual simple: Si es la primera nota, la marcamos como "seleccionada" (negra)
                    // Solo para imitar el diseño visual por ahora
                    val isFirst = state.notes.firstOrNull() == note

                    NoteItem(
                        note = note,
                        isToday = isFirst,
                        onClick = {
                            navController.navigate("add_edit_note?noteId=${note.id}")
                        }
                    )

                    // Línea separadora sutil
                    HorizontalDivider(
                        modifier = Modifier.padding(start = 80.dp), // Indentada a la derecha
                        thickness = 0.5.dp,
                        color = Color.LightGray.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}