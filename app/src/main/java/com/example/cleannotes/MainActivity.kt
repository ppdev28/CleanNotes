package com.example.cleannotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cleannotes.presentation.notes.AllNotesScreen
import com.example.cleannotes.presentation.notes.NotesScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "all_notes_screen"
                    ) {
                        // Pantalla Home
                        composable(route = "notes_screen") {
                            NotesScreen(navController = navController)
                        }
                        composable(route = "all_notes_screen") {
                            AllNotesScreen(navController = navController)
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
                            // Por ahora pondremos un texto simple hasta que diseñemos esta pantalla
                            // Lo haremos en el siguiente paso, pero para que no crashee:
                            Text("Pantalla de Edición (En construcción)")
                        }
                    }
                }
            }
        }
    }
}