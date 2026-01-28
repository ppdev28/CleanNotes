package com.example.cleannotes.domain.model

data class Note(
    val id: Int? = null, // Es nulo al crearla, la BD le dará un ID después
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int // Guardaremos el color como un entero (ARGB)
)

// Una pequeña excepción personalizada para validar datos más tarde
class InvalidNoteException(message: String): Exception(message)