package com.example.cleannotes.domain.model

data class Note(
    val id: Int? = null, // Es nulo al crearla, la BD le dará un ID después
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int // Guardaremos el color como un entero (ARGB)
){
    // 👇 ESTO ES LO QUE TE FALTA
    companion object {
        val colors = listOf(
            0xFFCF94DA.toInt(), // Violeta Pastel
            0xFF99CCFF.toInt(), // Azul Pastel
            0xFF99FF99.toInt(), // Verde Pastel
            0xFFFF9999.toInt(), // Rosa Pastel
            0xFFFFFF99.toInt()  // Amarillo Pastel
        )
    }
}

// Una pequeña excepción personalizada para validar datos más tarde
class InvalidNoteException(message: String): Exception(message)