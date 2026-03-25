package com.example.cleannotes.presentation.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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

	// `selectedTabIndex` is no longer needed for the tabs within HomeScreen, as navigation is now via BottomNav.

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
			CustomTopBar(navController)
		},
		bottomBar = {
			BottomNavigationBar(navController = navController)
		} // Replaced HomeBottomBar with BottomNavigationBar
	) { padding ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(padding)
		) {
			// Removed PrimaryTabRow from here

			val groupedNotes =
				state.notes.groupBy { getDayNumber(it.timestamp) + getMonthName(it.timestamp) }

			LazyColumn(
				modifier = Modifier.fillMaxSize(),
				contentPadding = PaddingValues(top = 8.dp, bottom = 0.dp) // Adjusted bottom padding as BottomNavigationBar is now present
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
		}
	}
}