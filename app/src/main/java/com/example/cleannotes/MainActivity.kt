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
import com.example.cleannotes.presentation.ui.search.SearchScreen
import com.example.cleannotes.presentation.ui.settings.SettingsScreen
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
						// Search Screen
						composable(route = "search_screen") {
							SearchScreen(navController = navController)
						}
						// Settings Screen
						composable(route = "settings_screen") {
							SettingsScreen(navController = navController)
						}

						// Pantalla Añadir/Editar
						// Aceptamos argumentos opcionales
						composable(
							route = "edit_note?noteId={noteId}&isReminder={isReminder}",
							arguments = listOf(
								navArgument(name = "noteId") {
									type = NavType.IntType
									defaultValue = -1
								},
								navArgument(name = "isReminder") {
									type = NavType.BoolType
									defaultValue = false
								}
							)
						) {
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