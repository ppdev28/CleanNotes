package com.example.cleannotes.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cleannotes.domain.model.Note
import com.example.cleannotes.presentation.util.DateUtils

@Composable
fun HomeNoteItem(
	note: Note,
	modifier: Modifier = Modifier,
	isToday: Boolean = true,
	onClick: () -> Unit
) {
	Box(
		modifier = modifier
			.clickable(onClick = onClick)
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
					color = MaterialTheme.colorScheme.onSurfaceVariant
				)

				Spacer(modifier = Modifier.height(4.dp))

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
							text = "TAG example",
							modifier = Modifier.padding(horizontal = 12.dp),
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