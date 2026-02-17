package com.example.cleannotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cleannotes.presentation.ui.edit_note.EditNoteScreen
import com.example.cleannotes.presentation.ui.all_notes.AllNotesScreen
import com.example.cleannotes.presentation.ui.all_reminders.AllRemindersScreen
import com.example.cleannotes.presentation.ui.theme.CleanNotesTheme
import com.example.cleannotes.presentation.ui.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			CleanNotesTheme {
				Surface(color = MaterialTheme.colorScheme.background) {
					val navController = rememberNavController()
					
					NavHost(
						navController = navController,
						startDestination = "home_screen"
					) {
						// Home Screen
						composable(route = "home_screen") {
							HomeScreen(navController = navController)
						}
						composable(route = "all_notes_screen") {
							AllNotesScreen(navController = navController)
						}
						composable(route = "all_reminders_screen") {
							AllRemindersScreen(navController = navController)
						}
						
						// Pantalla Añadir/Editar
						// Aceptamos un argumento opcional ?noteId={noteId}
						composable(
							route = "add_edit_note?noteId={noteId}",
							arguments = listOf(
								navArgument(name = "noteId") {
									type = NavType.IntType
									defaultValue = -1
								}
							)
						) {
							
							// ¡AQUÍ LLAMAMOS A LA PANTALLA REAL!
							EditNoteScreen(
								navController = navController
							)
						}
					}
				}
			}
		}
	}
}