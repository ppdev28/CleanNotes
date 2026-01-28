package com.example.cleannotes.presentation.notes.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.cleannotes.domain.model.Note

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    cutCornerSize: Dp = 30.dp, // El pequeño doblez de la esquina (estilo post-it)
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        // Dibujamos el fondo personalizado con Canvas para hacer el efecto de página doblada
        // O usamos una Card simple si prefieres el estilo iOS moderno plano.
        // Vamos con estilo iOS moderno (Card simple y limpia):

        Card(
            shape = MaterialTheme.shapes.medium, // Bordes redondeados
            colors = CardDefaults.cardColors(containerColor = Color(note.color)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = note.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray,
                    maxLines = 10,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // Botón de borrar (oculto o visible según diseño, aquí lo pondremos flotando pequeño)
        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete note",
                tint = Color.DarkGray
            )
        }
    }
}