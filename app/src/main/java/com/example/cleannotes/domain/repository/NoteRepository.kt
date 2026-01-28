package com.example.cleannotes.domain.repository

import com.example.cleannotes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    // Devuelve un flujo de lista de notas (Reactivo)
    fun getNotes(): Flow<List<Note>>

    // Devuelve una nota específica por ID
    suspend fun getNoteById(id: Int): Note?

    // Inserta una nota nueva o actualiza una existente
    suspend fun insertNote(note: Note)

    // Borra una nota
    suspend fun deleteNote(note: Note)
}
