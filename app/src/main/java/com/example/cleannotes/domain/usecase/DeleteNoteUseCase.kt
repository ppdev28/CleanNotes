package com.example.cleannotes.domain.usecase

import com.example.cleannotes.domain.model.Note
import com.example.cleannotes.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}