package com.example.cleannotes.domain.usecase

import com.example.cleannotes.domain.model.Note
import com.example.cleannotes.domain.repository.NoteRepository
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    // Fíjate: Recibe 'id' y devuelve 'Note?' (suspend fun, NO Flow)
    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}