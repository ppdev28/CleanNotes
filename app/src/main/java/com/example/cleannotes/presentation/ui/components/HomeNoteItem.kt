package com.example.cleannotes.presentation.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StickyNote2
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cleannotes.domain.model.Note
import com.example.cleannotes.presentation.util.DateUtils.getDayNumber
import com.example.cleannotes.presentation.util.DateUtils.getMonthName
import com.example.cleannotes.presentation.util.DateUtils.getTime

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeNoteItem(
	notes: List<Note>,
	modifier: Modifier = Modifier,
	isToday: Boolean = true,
	onClick: (Note) -> Unit,
	onLongClick: (Note) -> Unit = {}
) {
	if (notes.isEmpty()) return
	val firstNote = notes.first()

	val isColor =
		if (isToday) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary.copy(
			alpha = 0.5f
		)

	Box(
		modifier = modifier
			.background(MaterialTheme.colorScheme.background)
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
						.background(isColor)
				) {
					Column(horizontalAlignment = Alignment.CenterHorizontally) {
						Text(
							text = getDayNumber(firstNote.timestamp),
							color = MaterialTheme.colorScheme.onBackground,
							fontWeight = FontWeight.Bold,
							fontSize = 22.sp
						)
						Text(
							text = getMonthName(firstNote.timestamp).uppercase(),
							color = MaterialTheme.colorScheme.onBackground,
							fontSize = 12.sp
						)
					}
				}
			}

			// --- COLUMNA 2: CONTENIDO APILADO ---
			Column(
				modifier = Modifier.weight(1f)
			) {
				notes.forEachIndexed { index, note ->
					Column(
						modifier = Modifier
							.fillMaxWidth()
							.clip(MaterialTheme.shapes.small)
							.combinedClickable(
								onClick = { onClick(note) },
								onLongClick = { onLongClick(note) }
							)
							.padding(vertical = 4.dp)
					) {
						Row(verticalAlignment = Alignment.CenterVertically) {
							Icon(
								imageVector = if (note.content == "Remember") Icons.Default.Notifications else Icons.AutoMirrored.Filled.StickyNote2,
								contentDescription = if (note.content == "Remember") "Reminder" else "Note",
								modifier = Modifier.size(14.dp).padding(end = 4.dp),
								tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
							)
							Text(
								text = getTime(note.timestamp),
								fontSize = 12.sp,
								color = MaterialTheme.colorScheme.onBackground,
								maxLines = 1,
								overflow = TextOverflow.Ellipsis
							)
						}

						Spacer(modifier = Modifier.height(2.dp))

						// Título
						Text(
							text = note.title.ifBlank { "Untitled Note" },
							fontSize = 20.sp,
							fontWeight = FontWeight.Bold,
							color = MaterialTheme.colorScheme.onBackground,
							maxLines = 1,
							overflow = TextOverflow.Ellipsis
						)
					}

					// Espaciador entre notas apiladas del mismo día
					if (index < notes.size - 1) {
						Spacer(modifier = Modifier.height(16.dp))
					}
				}
			}
		}
	}
}