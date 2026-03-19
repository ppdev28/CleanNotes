package com.example.cleannotes.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cleannotes.domain.model.Note
import com.example.cleannotes.presentation.util.DateUtils.getDayNumber
import com.example.cleannotes.presentation.util.DateUtils.getMonthName
import com.example.cleannotes.presentation.util.DateUtils.getTime

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
			.background(Color.White)
	) {
		Row(
			modifier = Modifier
				.padding(horizontal = 24.dp, vertical = 12.dp)
				.fillMaxWidth(),
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
						.size(64.dp)
						.clip(CircleShape)
						.background(if (isToday) Color.Black else Color.LightGray.copy(alpha = 0.5f))
				) {
					Column(horizontalAlignment = Alignment.CenterHorizontally) {
						Text(
							text = getDayNumber(note.timestamp),
							color = if (isToday) Color.White else Color.Black,
							fontWeight = FontWeight.Bold,
							fontSize = 22.sp
						)
						if (isToday) {
							Text(
								text = getMonthName(note.timestamp).uppercase(),
								color = Color.White,
								fontSize = 12.sp
							)
						}
					}
				}
			}

			// --- COLUMNA 2: CONTENIDO ---
			Column(
				modifier = Modifier.weight(1f)
			) {
				// Time
				val timeText = if (isToday) {
					getTime(note.timestamp)
				} else {
					getTime(note.timestamp) + " " + getDayNumber(note.timestamp) + " " +
							getMonthName(note.timestamp)
				}

				Text(
					text = timeText,
					fontSize = 12.sp,
					color = Color.DarkGray
				)

				Spacer(modifier = Modifier.height(2.dp))

				// Título
				Text(
					text = note.title.ifBlank { "Untitled Note" },
					fontSize = 20.sp,
					fontWeight = FontWeight.Bold,
					color = Color.Black
				)

				Spacer(modifier = Modifier.height(6.dp))

				// Tags / Chips
				Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
					val defaultChips = listOf("YOUR", "IMPORTA")
					val contentWords = note.content.split(" ").filter { it.isNotBlank() }

					val chipsToShow = if (contentWords.isNotEmpty()) {
						contentWords.take(2).map { it.uppercase().take(7) }
					} else {
						defaultChips
					}

					chipsToShow.forEach { chipText ->
						Surface(
							shape = RoundedCornerShape(50),
							color = Color.LightGray.copy(alpha = 0.6f),
							modifier = Modifier.height(24.dp)
						) {
							Box(contentAlignment = Alignment.Center) {
								Text(
									text = chipText,
									modifier = Modifier.padding(horizontal = 12.dp),
									fontSize = 10.sp,
									fontWeight = FontWeight.Bold,
									color = Color.Black,
									maxLines = 1,
									overflow = TextOverflow.Ellipsis
								)
							}
						}
					}
				}
			}
		}
	}
}