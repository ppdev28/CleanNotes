package com.example.cleannotes.data.source

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cleannotes.domain.model.Note // Asegúrate de tener tu modelo de dominio creado

@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long
) {
    // Mapper: Entity -> Domain
    fun toNote(): Note {
        return Note(
            id = id,
            title = title,
            content = content,
            timestamp = timestamp
        )
    }
}

// Mapper: Domain -> Entity
fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp
    )
}