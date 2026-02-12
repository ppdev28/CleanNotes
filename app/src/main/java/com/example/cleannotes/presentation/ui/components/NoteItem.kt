package com.example.cleannotes.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cleannotes.domain.model.Note
import com.example.cleannotes.presentation.util.DateUtils

@Composable
fun NoteItem(
	note: Note,
	modifier: Modifier = Modifier,
	// Un parámetro visual para simular el estilo "Seleccionado" (Negro) o normal (Gris)
	isToday: Boolean = true,
	onClick: () -> Unit
) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.padding(vertical = 12.dp, horizontal = 16.dp)
			.clickable { onClick() },
		verticalAlignment = Alignment.Top // Alineamos arriba como en la foto
	) {
		// --- COLUMNA 1: FECHA (Burbuja) ---
		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			modifier = Modifier.padding(end = 16.dp)
		) {
			Box(
				contentAlignment = Alignment.Center,
				modifier = Modifier
					.size(50.dp) // Tamaño del círculo
					.clip(CircleShape)
					.background(if (isToday) Color.Black else Color(0xFFE0E0E0))
			) {
				Column(horizontalAlignment = Alignment.CenterHorizontally) {
					Text(
						text = DateUtils.getDayNumber(note.timestamp),
						color = if (isToday) Color.White else Color.Black,
						fontWeight = FontWeight.Bold,
						fontSize = 18.sp
					)
					Text(
						text = DateUtils.getMonthName(note.timestamp),
						color = if (isToday) Color.White else Color.Black,
						fontSize = 10.sp
					)
				}
			}
		}
		
		// --- COLUMNA 2: CONTENIDO ---
		Column(
			modifier = Modifier.weight(1f)
		) {
			// Hora
			Text(
				text = "${DateUtils.getTime(note.timestamp)} - 2:00 PM", // Hora hardcodeada final por ahora
				fontSize = 12.sp,
				color = Color.Gray
			)
			
			Spacer(modifier = Modifier.height(4.dp))
			
			// Título
			Text(
				text = note.title,
				fontSize = 20.sp,
				fontWeight = FontWeight.Bold,
				color = Color.Black
			)
			
			Spacer(modifier = Modifier.height(8.dp))
			
			// Chips / Etiquetas (Simulados con el color de la nota)
			Row {
				Surface(
					shape = RoundedCornerShape(50),
					color = Color(0xFFE0E0E0), // Gris claro de fondo
					modifier = Modifier.height(24.dp)
				) {
					Text(
						text = "PERSONAL", // Texto dummy o podrías usar note.content corto
						modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
						fontSize = 10.sp,
						fontWeight = FontWeight.Bold,
						color = Color.Black
					)
				}
			}
		}
	}
}