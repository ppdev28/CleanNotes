package com.example.cleannotes.presentation.ui.edit_note

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material.icons.outlined.Draw
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FormatAlignJustify
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cleannotes.presentation.ui.edit_note.viewmodel.AddEditNoteViewModel
import com.example.cleannotes.presentation.ui.theme.CleanNotesTheme

@Composable
fun EditNoteScreen(
	navController: NavController,
	viewModel: AddEditNoteViewModel = hiltViewModel()
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
					onDrawClick = { /* Implementar dibujo */ },
					onNewNoteClick = { /* Implementar nueva nota */ }
				)
			}
		) { padding ->
			EditNoteContent(
				title = titleState,
				content = contentState,
				onTitleChange = { viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it)) },
				onContentChange = { viewModel.onEvent(AddEditNoteEvent.EnteredContent(it)) },
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
		title = {
			Text(
				text = title,
				maxLines = 1,
				fontWeight = FontWeight.Bold,
				fontSize = 18.sp
			)
		},
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
			)
		)
		Spacer(modifier = Modifier.height(8.dp))
		Text(
			text = "",
			color = Color(0xFFFF9800),
			style = MaterialTheme.typography.bodyMedium
		)
		Spacer(modifier = Modifier.height(16.dp))
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
			modifier = Modifier.fillMaxHeight()
		)
		Spacer(modifier = Modifier.height(16.dp))
	}
}

@Composable
fun EditNoteBottomBar(
	onListClick: () -> Unit,
	onAttachClick: () -> Unit,
	onFormatClick: () -> Unit,
	onDrawClick: () -> Unit,
	onNewNoteClick: () -> Unit
) {
	BottomAppBar {
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
			IconButton(onClick = onDrawClick) {
				Icon(Icons.Outlined.Draw, contentDescription = "Dibujar")
			}
			IconButton(onClick = onNewNoteClick) {
				Icon(Icons.Outlined.Edit, contentDescription = "Nueva nota")
			}
		}
	}
}