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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cleannotes.domain.model.Note
import com.example.cleannotes.presentation.ui.home.viewmodel.NotesViewModel
import com.example.cleannotes.presentation.ui.components.BottomNavigationBar
import com.example.cleannotes.presentation.ui.components.HomeNoteItem
import com.example.cleannotes.presentation.util.DateUtils.getDayNumber
import com.example.cleannotes.presentation.util.DateUtils.getMonthName

@Composable
fun AllNotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel() // Reutilizamos el mismo VM
) {
    val state by viewModel.state.collectAsState()

    // Filtramos para mostrar solo las notas (cuyo contenido no es exclusivamente "Remember")
    val notesList = state.notes.filter { it.content != "Remember" }

    var noteToDelete by remember { mutableStateOf<Note?>(null) }

    if (noteToDelete != null) {
        AlertDialog(
            onDismissRequest = { noteToDelete = null },
            title = { Text("Eliminar nota") },
            text = { Text("¿Estás seguro que deseas eliminar esta nota?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        noteToDelete?.let { viewModel.onDeleteNote(it) }
                        noteToDelete = null
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { noteToDelete = null }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.StickyNote2,
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
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Todas las Notas",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (notesList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay notas disponibles", color = Color.Gray)
                }
            } else {
                val groupedNotes = notesList.groupBy { getDayNumber(it.timestamp) + getMonthName(it.timestamp) }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(groupedNotes.entries.toList()) { (key, notesOfDay) ->
                        val isFirst = groupedNotes.keys.firstOrNull() == key
                        HomeNoteItem(
                            notes = notesOfDay,
                            isToday = isFirst,
                            onClick = { note ->
                                navController.navigate("edit_note?noteId=${note.id}")
                            },
                            onLongClick = { note ->
                                noteToDelete = note
                            }
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(start = 104.dp, end = 24.dp, top = 8.dp, bottom = 8.dp),
                            thickness = 1.dp,
                            color = Color.LightGray.copy(alpha = 0.4f)
                        )
                    }
                }
            }
        }
    }
}