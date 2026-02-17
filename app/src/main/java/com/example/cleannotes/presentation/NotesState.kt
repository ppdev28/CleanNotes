package com.example.cleannotes.presentation

import com.example.cleannotes.domain.model.Note

data class NotesState(
	val notes: List<Note> = emptyList(), // La lista de notas
	val isLoading: Boolean = false,      // ¿Estamos cargando?
    // val error: String? = null         // Podríamos añadir errores aquí
)