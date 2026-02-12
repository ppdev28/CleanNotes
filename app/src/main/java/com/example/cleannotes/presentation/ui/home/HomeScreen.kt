package com.example.cleannotes.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cleannotes.presentation.ui.NotesViewModel
import com.example.cleannotes.presentation.ui.components.HomeBottomBar
import com.example.cleannotes.presentation.ui.components.NoteItem
import androidx.navigation.compose.rememberNavController
import com.example.cleannotes.domain.model.Note
@Composable
fun HomeScreen(
	navController: NavController,
	viewModel: NotesViewModel = hiltViewModel()
) {
	val state by viewModel.state.collectAsState()
	
	Scaffold(
		containerColor = Color(0xFFF8F8F8),
		// Eliminamos el 'floatingActionButton' antiguo
		
		// Usamos bottomBar para la barra de escritura
		bottomBar = {
			// Añadimos un poco de padding abajo para que no pegue con el borde de la pantalla (gestos)
			// y ponemos el fondo blanco para tapar la lista cuando hace scroll
			Box(
				modifier = Modifier
					.background(Color(0xFFF8F8F8))
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
				.padding(padding) // Esto asegura que la lista no quede tapada por la barra
		) {
			// --- CABECERA ---
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(top = 16.dp, start = 24.dp, end = 24.dp, bottom = 8.dp),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically
			) {
				Text(
					text = "NOTAS DE HOY",
					color = Color.Black
				)
				
				Box(
					modifier = Modifier
						.size(40.dp)
						.clip(CircleShape)
						.border(1.dp, Color.Gray, CircleShape)
						.background(Color.LightGray),
					contentAlignment = Alignment.Center
				) {
					Icon(
						imageVector = Icons.Default.Person,
						contentDescription = "Profile",
						tint = Color.White
					)
				}
			}
			
			// --- LISTA DE NOTAS ---
			LazyColumn(
				modifier = Modifier.fillMaxSize(),
				contentPadding = PaddingValues(bottom = 20.dp)
			) {
				items(state.notes) { note ->
					val isFirst = state.notes.firstOrNull() == note
					
					NoteItem(
						note = note,
						isToday = isFirst,
						onClick = {
							navController.navigate("add_edit_note?noteId=${note.id}")
						}
					)
					
					HorizontalDivider(
						modifier = Modifier.padding(start = 80.dp),
						thickness = 0.5.dp,
						color = Color.LightGray.copy(alpha = 0.5f)
					)
				}
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
	val navController = rememberNavController()
	val notes = listOf(
		Note(
			id = 1,
			title = "Hacer la compra",
			content = "Leche, pan, huevos",
			timestamp = System.currentTimeMillis()
		),
		Note(
			id = 2,
			title = "Llamar al médico",
			content = "Pedir cita para el viernes",
			timestamp = System.currentTimeMillis() - 1000 * 60 * 60
		),
		Note(
			id = 3,
			title = "Terminar el informe",
			content = "Revisar las últimas secciones",
			timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 24
		)
	)
	
	Scaffold(
		containerColor = Color(0xFFF8F8F8),
		bottomBar = {
			Box(
				modifier = Modifier
					.background(Color(0xFFF8F8F8))
					.padding(bottom = 16.dp)
			) {
				HomeBottomBar(
					onQuickAdd = { _, _ -> }
				)
			}
		}
	) { padding ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(padding)
		) {
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(top = 16.dp, start = 24.dp, end = 24.dp, bottom = 8.dp),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically
			) {
				Text(
					text = "NOTAS DE HOY",
					color = Color.Black,
				)
				
				Box(
					modifier = Modifier
						.size(40.dp)
						.clip(CircleShape)
						.border(1.dp, Color.Gray, CircleShape)
						.background(Color.LightGray),
					contentAlignment = Alignment.Center,
					
					) {
					Icon(
						imageVector = Icons.Default.Person,
						contentDescription = "Profile",
						tint = Color.White
					)
				}
			}
			
			LazyColumn(
				modifier = Modifier.fillMaxSize(),
				contentPadding = PaddingValues(bottom = 20.dp)
			) {
				items(notes) { note ->
					val isFirst = notes.firstOrNull() == note
					
					NoteItem(
						note = note,
						isToday = isFirst,
						onClick = {
							navController.navigate("add_edit_note?noteId=${note.id}")
						}
					)
					
					HorizontalDivider(
						modifier = Modifier.padding(start = 80.dp),
						thickness = 0.5.dp,
						color = Color.LightGray.copy(alpha = 0.5f)
					)
				}
			}
		}
	}
}