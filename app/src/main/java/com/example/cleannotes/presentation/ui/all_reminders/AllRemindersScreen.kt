package com.example.cleannotes.presentation.ui.all_reminders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cleannotes.domain.model.Note
import com.example.cleannotes.presentation.ui.home.viewmodel.NotesViewModel
import com.example.cleannotes.presentation.ui.components.CustomTopBar
import com.example.cleannotes.presentation.ui.components.HomeBottomBar
import com.example.cleannotes.presentation.ui.components.HomeNoteItem
import com.example.cleannotes.presentation.util.DateUtils.getDayNumber
import com.example.cleannotes.presentation.util.DateUtils.getMonthName

@Composable
fun AllRemindersScreen(
	navController: NavController,
	viewModel: NotesViewModel = hiltViewModel()
) {
	val state by viewModel.state.collectAsState()

	val remindersList = state.notes.filter { it.content == "Remember" }

	var noteToDelete by remember { mutableStateOf<Note?>(null) }
	val showDeleteDialog = remember { mutableStateOf(false) }

	if (showDeleteDialog.value) {
		AlertDialog(
			onDismissRequest = { showDeleteDialog.value = false },
			title = { Text("Eliminar recordatorio") },
			text = { Text("¿Estás seguro que deseas eliminar este recordatorio?") },
			confirmButton = {
				TextButton(
					onClick = {
						noteToDelete?.let { viewModel.onDeleteNote(it) }
						showDeleteDialog.value = false
						noteToDelete = null
					}
				) {
					Text("Eliminar")
				}
			},
			dismissButton = {
				TextButton(
					onClick = { showDeleteDialog.value = false }
				) {
					Text("Cancelar")
				}
			}
		)
	}

	Scaffold(
		containerColor = MaterialTheme.colorScheme.background,
		topBar = {
			CustomTopBar(navController)
		},
		bottomBar = {
			Box(
				modifier = Modifier
					.background(Color.White)
					.padding(bottom = 16.dp)
			) {
				HomeBottomBar(
					onQuickAdd = { text, isReminder ->
						viewModel.onQuickAdd(text, isReminder)
					}
				)
			}
		}
	) { padding ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(padding)
		) {
			Text(
				text = "Recordatorios",
				fontSize = 32.sp,
				fontWeight = FontWeight.Bold,
				color = Color.Black,
				modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
			)

			if (remindersList.isEmpty()) {
				Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
					Text("No hay recordatorios pendientes", color = Color.Gray)
				}
			} else {
				val groupedNotes =
					remindersList.groupBy { getDayNumber(it.timestamp) + getMonthName(it.timestamp) }

				LazyColumn(
					modifier = Modifier.fillMaxSize(),
					contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp)
				) {
					items(groupedNotes.entries.toList()) { (key, notesOfDay) ->
						val isFirst = groupedNotes.keys.firstOrNull() == key

						HomeNoteItem(
							notes = notesOfDay,
							isToday = isFirst,
							onClick = { note ->
								navController.navigate("edit_note?noteId=${note.id}")
							},
							onLongClick = { note ->
								noteToDelete = note
								showDeleteDialog.value = true
							}
						)
						HorizontalDivider(
							modifier = Modifier.padding(start = 104.dp, end = 24.dp, top = 8.dp, bottom = 8.dp),
							thickness = 1.dp,
							color = Color.LightGray.copy(alpha = 0.4f)
						)
					}
				}
			}
		}
	}
}