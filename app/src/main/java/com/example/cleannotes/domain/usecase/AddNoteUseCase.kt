package com.example.cleannotes.domain.usecase

import com.example.cleannotes.domain.model.InvalidNoteException
import com.example.cleannotes.domain.model.Note
import com.example.cleannotes.domain.repository.NoteRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    // ↓↓↓ ¡ESTA PALABRA ES LA CLAVE! ↓↓↓
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("El título no puede estar vacío.")
        }
        // ... validaciones ...
        repository.insertNote(note)
    }
}