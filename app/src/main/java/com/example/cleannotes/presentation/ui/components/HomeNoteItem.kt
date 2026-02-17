package com.example.cleannotes.presentation.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeNoteItem(
	note: Note,
	modifier: Modifier = Modifier,
	isToday: Boolean = true,
	onClick: () -> Unit
) {
	Card(
		modifier = modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp, vertical = 8.dp),
		elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
		shape = RoundedCornerShape(12.dp),
		onClick = onClick,
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surface
		)
	) {
		Row(
			modifier = Modifier.padding(16.dp),
			verticalAlignment = Alignment.Top
		) {
			// --- COLUMNA 1: FECHA (Burbuja) ---
			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				modifier = Modifier.padding(end = 16.dp)
			) {
				Box(
					contentAlignment = Alignment.Center,
					modifier = Modifier
						.size(50.dp)
						.clip(CircleShape)
						.background(if (isToday) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)
				) {
					Column(horizontalAlignment = Alignment.CenterHorizontally) {
						Text(
							text = DateUtils.getDayNumber(note.timestamp),
							color = MaterialTheme.colorScheme.onPrimary,
							fontWeight = FontWeight.Bold,
							fontSize = 18.sp
						)
						Text(
							text = DateUtils.getMonthName(note.timestamp),
							color = MaterialTheme.colorScheme.onPrimary,
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
					text = DateUtils.getTime(note.timestamp),
					fontSize = 12.sp,
					color = Color.Gray
				)

				Spacer(modifier = Modifier.height(2.dp))

				// Título
				Text(
					text = note.title,
					fontSize = 20.sp,
					fontWeight = FontWeight.Bold,
					color = MaterialTheme.colorScheme.onBackground
				)

				Spacer(modifier = Modifier.height(8.dp))

				// Contenido corto a modo de Chip
				if (note.content.isNotBlank()) {
					Surface(
						shape = RoundedCornerShape(50),
						color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
						modifier = Modifier.height(24.dp)
					) {
						Text(
							text = note.content,
							modifier = Modifier.padding(horizontal = 12.dp, vertical = 0.dp),
							fontSize = 10.sp,
							fontWeight = FontWeight.Bold,
							color = MaterialTheme.colorScheme.onSecondary,
							maxLines = 1
						)
					}
				}
			}
		}
	}
}