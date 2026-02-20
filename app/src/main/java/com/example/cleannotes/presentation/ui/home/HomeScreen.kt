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
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cleannotes.presentation.ui.components.CustomTopBar
import com.example.cleannotes.presentation.ui.components.HomeBottomBar
import com.example.cleannotes.presentation.ui.components.HomeNoteItem
import com.example.cleannotes.presentation.ui.home.viewmodel.NotesViewModel
import com.example.cleannotes.presentation.util.DateUtils

@Composable
fun HomeScreen(
	navController: NavController,
	viewModel: NotesViewModel = hiltViewModel()
) {
	val state by viewModel.state.collectAsState()

	Scaffold(
		containerColor = MaterialTheme.colorScheme.background,
		topBar = {
			CustomTopBar(navController)
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
			Text(
				text = "Today, ${DateUtils.getDayNumber(System.currentTimeMillis())} ${
					DateUtils.getMonthName(
						System.currentTimeMillis()
					)
				}",
				modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
			)

			HorizontalDivider(
				modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp),
				thickness = 1.dp,
				color = MaterialTheme.colorScheme.onBackground
			)
			LazyColumn(
				modifier = Modifier.fillMaxSize(),
				contentPadding = PaddingValues(top = 12.dp, bottom = 16.dp)
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
						modifier = Modifier.padding(horizontal = 30.dp, vertical = 4.dp),
						thickness = 1.dp,
						color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
					)
				}
			}
		}
	}
}