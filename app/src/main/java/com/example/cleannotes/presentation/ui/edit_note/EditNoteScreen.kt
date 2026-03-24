package com.example.cleannotes.presentation.ui.edit_note

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material.icons.outlined.FormatAlignJustify
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cleannotes.presentation.ui.edit_note.viewmodel.EditNoteViewModel
import com.example.cleannotes.presentation.ui.theme.CleanNotesTheme

@Composable
fun EditNoteScreen(
	navController: NavController,
	viewModel: EditNoteViewModel = hiltViewModel()
) {
	val titleState = viewModel.noteTitle.value
	val contentState = viewModel.noteContent.value

	CleanNotesTheme {
		Scaffold(
			topBar = {
				EditNoteTopBar(
					title = titleState,
					onBackClick = { navController.navigateUp() },
					onShareClick = { /* Implementar compartir */ },
					onMoreClick = { /* Implementar más opciones */ }
				)
			},
			bottomBar = {
				EditNoteBottomBar(
					onListClick = { /* Implementar lista */ },
					onAttachClick = { /* Implementar adjuntar */ },
					onFormatClick = { /* Implementar formato */ },
				)
			}
		) { padding ->
			EditNoteContent(
				title = titleState,
				content = contentState,
				onTitleChange = { viewModel.onEvent(EditNoteEvent.EnteredTitle(it)) },
				onContentChange = { viewModel.onEvent(EditNoteEvent.EnteredContent(it)) },
				modifier = Modifier.padding(padding)
			)
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteTopBar(
	title: String,
	onBackClick: () -> Unit,
	onShareClick: () -> Unit,
	onMoreClick: () -> Unit
) {
	TopAppBar(
		title = {},
		navigationIcon = {
			IconButton(onClick = onBackClick) {
				Icon(
					imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
					contentDescription = "Atrás"
				)
			}
		},
		actions = {
			IconButton(onClick = onShareClick) {
				Icon(
					imageVector = Icons.Outlined.Share,
					contentDescription = "Compartir"
				)
			}
			IconButton(onClick = onMoreClick) {
				Icon(
					imageVector = Icons.Default.MoreHoriz,
					contentDescription = "Más opciones"
				)
			}
		}
	)
}

@Composable
fun EditNoteContent(
	title: String,
	content: String,
	onTitleChange: (String) -> Unit,
	onContentChange: (String) -> Unit,
	modifier: Modifier = Modifier
) {
	val scrollState = rememberScrollState()
	Column(
		modifier = modifier
			.fillMaxSize()
			.padding(16.dp)
			.verticalScroll(scrollState)
	) {
		// Card for Title
		Card(
			elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
			colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
			modifier = Modifier.fillMaxWidth()
		) {
			TextField(
				value = title,
				onValueChange = { onTitleChange(it) },
				placeholder = { Text("Title") },
				textStyle = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
				colors = TextFieldDefaults.colors(
					focusedContainerColor = Color.Transparent,
					unfocusedContainerColor = Color.Transparent,
					disabledContainerColor = Color.Transparent,
					focusedIndicatorColor = Color.Transparent,
					unfocusedIndicatorColor = Color.Transparent,
					disabledIndicatorColor = Color.Transparent,
				),
				modifier = Modifier.padding(16.dp)
			)
		}

		Spacer(modifier = Modifier.height(16.dp))

		// Card for Content
		TextField(
			value = content,
			onValueChange = { onContentChange(it) },
			placeholder = { Text("Content") },
			textStyle = MaterialTheme.typography.bodyLarge,
			colors = TextFieldDefaults.colors(
				focusedContainerColor = Color.Transparent,
				unfocusedContainerColor = Color.Transparent,
				disabledContainerColor = Color.Transparent,
				focusedIndicatorColor = Color.Transparent,
				unfocusedIndicatorColor = Color.Transparent,
				disabledIndicatorColor = Color.Transparent,
			),
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp)
		)
	}
	Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun EditNoteBottomBar(
	onListClick: () -> Unit,
	onAttachClick: () -> Unit,
	onFormatClick: () -> Unit
) {
	BottomAppBar(modifier = Modifier.background(MaterialTheme.colorScheme.primary)) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceEvenly
		) {
			IconButton(onClick = onListClick) {
				Icon(Icons.AutoMirrored.Outlined.List, contentDescription = "Lista")
			}
			IconButton(onClick = onAttachClick) {
				Icon(Icons.Outlined.AttachFile, contentDescription = "Adjuntar")
			}
			IconButton(onClick = onFormatClick) {
				Icon(Icons.Outlined.FormatAlignJustify, contentDescription = "Formato")
			}
		}
	}
}