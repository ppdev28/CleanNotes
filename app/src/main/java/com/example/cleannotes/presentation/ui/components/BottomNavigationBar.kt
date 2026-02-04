package com.example.cleannotes.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    NavigationBar(
        containerColor = Color.White, // Fondo blanco
        contentColor = Color.Black
    ) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        // Botón HOME (Pantalla anterior Timeline)
        NavigationBarItem(
            selected = currentRoute == "notes_screen",
            onClick = { navController.navigate("notes_screen") },
            icon = {
                Icon(
                    imageVector = if (currentRoute == "notes_screen") Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Home"
                )
            },
            colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent) // Sin burbuja de selección
        )

        // Botón NOTES (Esta pantalla nueva)
        NavigationBarItem(
            selected = currentRoute == "all_notes_screen",
            onClick = { navController.navigate("all_notes_screen") },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Description, // Icono cuadrado/nota
                    contentDescription = "Notes"
                )
            },
            colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
        )

        // Botón SEARCH (Futuro)
        NavigationBarItem(
            selected = false,
            onClick = { /* Todo: Implement Search */ },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search"
                )
            },
            colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
        )
    }
}