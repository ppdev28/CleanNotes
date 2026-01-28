package com.example.cleannotes
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cleannotes.presentation.notes.NotesScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // <--- ¡NO OLVIDES ESTO!
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Usamos el tema por defecto de Material por ahora
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "notes_screen"
                    ) {
                        composable(route = "notes_screen") {
                            NotesScreen(navController = navController)
                        }

                        // Aquí añadiremos la pantalla de "add_edit_note" pronto
                    }
                }
            }
        }
    }
}