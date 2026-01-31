package com.example.cleannotes.presentation.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cleannotes.domain.model.Note
import com.example.cleannotes.presentation.notes.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor: Int, // Recibimos el color inicial o el actual si quisiéramos pasarlo por argumento
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value
    val colorState = viewModel.noteColor.value

    val snackbarHostState = remember { SnackbarHostState() }

    // Escuchamos eventos de una sola vez (Guardado exitoso o Error)
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditNoteViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(message = event.message)
                }
                is AddEditNoteViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp() // Volver atrás al guardar
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.saveNote() },
                containerColor = Color.Black, // Estilo minimalista
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save note")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color(colorState) // El fondo cambia según el color elegido
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // --- SELECCIÓN DE COLOR ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.colors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(Color(color))
                            .border(
                                width = 3.dp,
                                color = if (viewModel.noteColor.value == color) {
                                    Color.Black // Borde negro si está seleccionado
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                viewModel.onColorChange(color)
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- TÍTULO ---
            TransparentHintTextField(
                text = titleState,
                hint = "Título...",
                onValueChange = { viewModel.onTitleChange(it) },
                onFocusChange = { /* Podríamos manejar estados de foco aquí */ },
                isHintVisible = titleState.isBlank(),
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineMedium.copy(color = Color.Black) // Usamos Lexend
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- CONTENIDO ---
            TransparentHintTextField(
                text = contentState,
                hint = "Escribe tu idea...",
                onValueChange = { viewModel.onContentChange(it) },
                onFocusChange = { },
                isHintVisible = contentState.isBlank(),
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.DarkGray), // Usamos Lexend
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}