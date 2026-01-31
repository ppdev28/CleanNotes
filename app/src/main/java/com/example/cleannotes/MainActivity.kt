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
import com.example.cleannotes.presentation.notes.AddEditNoteScreen
import com.example.cleannotes.presentation.notes.AllNotesScreen
import com.example.cleannotes.presentation.notes.AllRemindersScreen
import com.example.cleannotes.presentation.notes.NotesScreen
import com.example.cleannotes.presentation.ui.theme.CleanNotesTheme
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
                        startDestination = "notes_screen"
                    ) {
                        // Home Screen
                        composable(route = "notes_screen") {
                            NotesScreen(navController = navController)
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
                            // Obtenemos el color si lo pasamos (o -1 si no)
                            val color = it.arguments?.getInt("noteColor") ?: -1

                            // ¡AQUÍ LLAMAMOS A LA PANTALLA REAL!
                            AddEditNoteScreen(
                                navController = navController,
                                noteColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}