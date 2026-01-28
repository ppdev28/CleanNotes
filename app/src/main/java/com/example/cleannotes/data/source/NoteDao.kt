package com.example.cleannotes.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteEntity?

    // CAMBIO 1: Ahora devuelve Long (el ID de la fila insertada)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    // CAMBIO 2: Ahora devuelve Int (el número de filas borradas)
    @Delete
    suspend fun deleteNote(note: NoteEntity): Int
}