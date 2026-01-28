package com.example.cleannotes.data.repository

import com.example.cleannotes.data.source.NoteDao
import com.example.cleannotes.data.source.toNoteEntity
import com.example.cleannotes.domain.model.Note
import com.example.cleannotes.domain.repository.NoteRepository // Tu interfaz de dominio
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes().map { entities ->
            entities.map { it.toNote() }
        }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)?.toNote()
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note.toNoteEntity())
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note.toNoteEntity())
    }
}