package com.example.cleannotes.domain.usecase

import com.example.cleannotes.domain.model.Note
import com.example.cleannotes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    // El operador 'invoke' nos permite llamar a la clase como si fuera una función
    operator fun invoke(): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            // Lógica de negocio: Ordenar por fecha descendente
            notes.sortedByDescending { it.timestamp }
        }
    }
}