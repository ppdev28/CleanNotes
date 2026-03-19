package com.example.cleannotes.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cleannotes.presentation.ui.components.CustomTopBar
import com.example.cleannotes.presentation.ui.components.HomeBottomBar
import com.example.cleannotes.presentation.ui.components.HomeNoteItem
import com.example.cleannotes.presentation.ui.home.viewmodel.NotesViewModel

@Composable
fun HomeScreen(
	navController: NavController,
	viewModel: NotesViewModel = hiltViewModel()
) {
	val state by viewModel.state.collectAsState()

	Scaffold(
		containerColor = Color.White, // Force white background
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
				text = "Notas de hoy",
				modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
				color = Color.Black,
				style = MaterialTheme.typography.bodyLarge
			)

			LazyColumn(
				modifier = Modifier.fillMaxSize(),
				contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
			) {
				items(state.notes) { note ->
					val isFirst = state.notes.firstOrNull() == note

					HomeNoteItem(
						note = note,
						isToday = isFirst,
						onClick = {
							navController.navigate("edit_note?noteId=${note.id}")
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