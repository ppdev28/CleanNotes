package com.example.cleannotes.presentation.ui.search

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cleannotes.domain.model.Note
import com.example.cleannotes.presentation.ui.components.HomeNoteItem
import com.example.cleannotes.presentation.ui.components.SearchBar
import com.example.cleannotes.presentation.ui.home.viewmodel.NotesViewModel
import com.example.cleannotes.presentation.util.DateUtils.getDayNumber
import com.example.cleannotes.presentation.util.DateUtils.getMonthName

@Composable
fun SearchScreen(
	navController: NavController,
	viewModel: NotesViewModel = hiltViewModel()
) {
	val state by viewModel.state.collectAsState()
	var searchQuery by remember { mutableStateOf("") }

	val showDeleteDialog = remember { mutableStateOf(false) }
	var noteToDelete by remember { mutableStateOf<Note?>(null) }

	val filteredNotes = if (searchQuery.isBlank()) {
		emptyList()
	} else {
		state.notes.filter {
			it.title.contains(searchQuery, ignoreCase = true) ||
					it.content.contains(searchQuery, ignoreCase = true)
		}
	}

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
		modifier = Modifier.padding(top = 32.dp),
		topBar = {
			SearchBar(
				query = searchQuery,
				onQueryChange = { searchQuery = it },
				onBackClick = { navController.popBackStack() }
			)
		}
	) { padding ->
		Column(
			modifier = Modifier
          .fillMaxSize()
          .padding(padding)
		) {
			if (searchQuery.isNotEmpty() && filteredNotes.isEmpty()) {
				Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
					Text("No se encontraron resultados para \"$searchQuery\"", color = Color.Gray)
				}
			} else if (searchQuery.isEmpty()) {
				Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
					Text("Comienza a escribir para buscar...", color = Color.Gray)
				}
			} else {
				val groupedNotes =
					filteredNotes.groupBy { getDayNumber(it.timestamp) + getMonthName(it.timestamp) }

				LazyColumn(
					modifier = Modifier.fillMaxSize(),
					contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
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
}