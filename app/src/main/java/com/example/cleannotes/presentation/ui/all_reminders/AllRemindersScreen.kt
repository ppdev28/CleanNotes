package com.example.cleannotes.presentation.ui.all_reminders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import com.example.cleannotes.presentation.ui.components.HomeBottomBar
import com.example.cleannotes.presentation.ui.components.HomeNoteItem
import com.example.cleannotes.presentation.ui.home.viewmodel.NotesViewModel
import com.example.cleannotes.presentation.util.DateUtils.getDayNumber
import com.example.cleannotes.presentation.util.DateUtils.getMonthName

@OptIn(ExperimentalMaterial3Api::class)
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
			TopAppBar(
				title = {
					Text(
						"Reminders",
						fontSize = 24.sp,
						fontWeight = FontWeight.ExtraLight,
						style = MaterialTheme.typography.titleLarge
					)
				},
				navigationIcon = {
					IconButton(onClick = { navController.navigateUp() }) {
						Icon(
							imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
							contentDescription = "Atrás",
						)
					}
				})
		},
		bottomBar = {
			Box(
				modifier = Modifier
					.background(MaterialTheme.colorScheme.background)
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