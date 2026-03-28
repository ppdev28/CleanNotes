package com.example.cleannotes.presentation.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cleannotes.domain.model.Note
import com.example.cleannotes.presentation.ui.components.BottomNavigationBar
import com.example.cleannotes.presentation.ui.components.CustomTopBar
import com.example.cleannotes.presentation.ui.components.HomeNoteItem
import com.example.cleannotes.presentation.ui.components.SimpleNoteItem
import com.example.cleannotes.presentation.ui.home.viewmodel.NotesViewModel
import com.example.cleannotes.presentation.util.DateUtils.getDayNumber
import com.example.cleannotes.presentation.util.DateUtils.getMonthName

@Composable
fun HomeScreen(
	navController: NavController,
	viewModel: NotesViewModel = hiltViewModel()
) {
	val state by viewModel.state.collectAsState()

	val showDeleteDialog = remember { mutableStateOf(false) }
	var noteToDelete by remember { mutableStateOf<Note?>(null) }
	var isListView by remember { mutableStateOf(true) }

	if (showDeleteDialog.value) {
		AlertDialog(
			onDismissRequest = { showDeleteDialog.value = false },
			title = { Text("Eliminar nota") },
			text = { Text("¿Estás seguro que deseas eliminar esta nota?") },
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
			CustomTopBar(
				navController = navController,
				isListView = isListView,
				onToggleView = { isListView = !isListView }
			)
		},
		bottomBar = {
			BottomNavigationBar(navController = navController)
		}
	) { padding ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(padding)
		) {
			if (isListView) {
				val groupedNotes =
					state.notes.groupBy { getDayNumber(it.timestamp) + getMonthName(it.timestamp) }

				LazyColumn(
					modifier = Modifier.fillMaxSize(),
					contentPadding = PaddingValues(top = 8.dp, bottom = 0.dp)
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
							modifier = Modifier.padding(start = 84.dp, end = 24.dp, top = 8.dp, bottom = 8.dp),
							thickness = 1.dp,
							color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
						)
					}
				}
			} else {
				LazyVerticalGrid(
					columns = GridCells.Fixed(2),
					modifier = Modifier
						.fillMaxSize()
						.padding(horizontal = 16.dp),
					contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp)
				) {
					items(state.notes) { note ->
						Box(modifier = Modifier.padding(8.dp)) {
							SimpleNoteItem(
								note = note,
								onClick = {
									navController.navigate("edit_note?noteId=${note.id}")
								}
							)
						}
					}
				}
			}
		}
	}
}