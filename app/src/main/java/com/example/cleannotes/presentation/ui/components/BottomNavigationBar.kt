package com.example.cleannotes.presentation.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StickyNote2
import androidx.compose.material.icons.automirrored.outlined.StickyNote2
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(
	navController: NavController
) {
	NavigationBar(
		containerColor = MaterialTheme.colorScheme.background, // Fondo blanco
		contentColor = MaterialTheme.colorScheme.onBackground,
		modifier = Modifier
			.height(72.dp)
			.shadow(4.dp)
	) {
		val navBackStackEntry = navController.currentBackStackEntryAsState()
		val currentRoute = navBackStackEntry.value?.destination?.route

		val isHomeRoute = currentRoute.equals("home_screen")
		val isNotesRoute = currentRoute.equals("all_notes_screen")
		val isRemindersRoute = currentRoute.equals("all_reminders_screen")
		val isSearchRoute = currentRoute.equals("search_screen")

		val homeIcon = Icons.Filled.Home.takeIf { isHomeRoute } ?: Icons.Outlined.Home
		val notesIcon = Icons.AutoMirrored.Filled.StickyNote2.takeIf { isNotesRoute }
			?: Icons.AutoMirrored.Outlined.StickyNote2
		val remindersIcon =
			Icons.Filled.Notifications.takeIf { isRemindersRoute } ?: Icons.Outlined.Notifications
		val searchIcon = Icons.Filled.Search.takeIf { isSearchRoute } ?: Icons.Outlined.Search


		// Botón HOME (Pantalla anterior Timeline)
		NavigationBarItem(
			selected = isHomeRoute,
			onClick = { navController.navigate("home_screen") },
			icon = {
				Icon(
					imageVector = homeIcon,
					contentDescription = "Home"
				)
			},
			colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent) // Sin burbuja de selección
		)

		// Botón NOTES (Esta pantalla nueva)
		NavigationBarItem(
			selected = isNotesRoute,
			onClick = { navController.navigate("all_notes_screen") },
			icon = {
				Icon(
					imageVector = notesIcon, // Icono cuadrado/nota
					contentDescription = "Notes"
				)
			},
			colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
		)

		// Botón NOTES (Esta pantalla nueva)
		NavigationBarItem(
			selected = isRemindersRoute,
			onClick = { navController.navigate("all_reminders_screen") },
			icon = {
				Icon(
					imageVector = remindersIcon, // Icono cuadrado/nota
					contentDescription = "Reminders"
				)
			},
			colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
		)

		// Botón SEARCH (Futuro)
		NavigationBarItem(
			selected = isSearchRoute,
			onClick = { /* Todo: Implement Search */ },
			icon = {
				Icon(
					imageVector = searchIcon,
					contentDescription = "Search"
				)
			},
			colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
		)
	}
}