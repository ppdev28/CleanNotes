package com.example.cleannotes.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StickyNote2
import androidx.compose.material.icons.automirrored.outlined.StickyNote2
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
	var showMenu by remember { mutableStateOf(false) }

	Box(modifier = Modifier.fillMaxWidth()) {
		NavigationBar(
			containerColor = MaterialTheme.colorScheme.background,
			contentColor = MaterialTheme.colorScheme.onBackground,
			modifier = Modifier
				.height(72.dp)
				.shadow(4.dp)
		) {
			val navBackStackEntry = navController.currentBackStackEntryAsState()
			val currentRoute = navBackStackEntry.value?.destination?.route

			val isHomeRoute = currentRoute?.startsWith("home_screen") == true
			val isNotesRoute = currentRoute?.startsWith("all_notes_screen") == true
			val isRemindersRoute = currentRoute?.startsWith("all_reminders_screen") == true
			val isSearchRoute = currentRoute?.startsWith("search_screen") == true

			val homeIcon = Icons.Filled.Home.takeIf { isHomeRoute } ?: Icons.Outlined.Home
			val notesIcon = Icons.AutoMirrored.Filled.StickyNote2.takeIf { isNotesRoute }
				?: Icons.AutoMirrored.Outlined.StickyNote2
			val remindersIcon =
				Icons.Filled.Notifications.takeIf { isRemindersRoute } ?: Icons.Outlined.Notifications
			val searchIcon = Icons.Filled.Search.takeIf { isSearchRoute } ?: Icons.Outlined.Search

			// Botón HOME
			NavigationBarItem(
				selected = isHomeRoute,
				onClick = { navController.navigate("home_screen") },
				icon = {
					Icon(
						imageVector = homeIcon,
						contentDescription = "Home"
					)
				},
				colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
			)

			// Botón NOTES
			NavigationBarItem(
				selected = isNotesRoute,
				onClick = { navController.navigate("all_notes_screen") },
				icon = {
					Icon(
						imageVector = notesIcon,
						contentDescription = "Notes"
					)
				},
				colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
			)

			// Placeholder para el FAB central
			NavigationBarItem(
				selected = false,
				onClick = { },
				icon = { Spacer(Modifier.size(32.dp)) },
				enabled = false
			)

			// Botón REMINDERS
			NavigationBarItem(
				selected = isRemindersRoute,
				onClick = { navController.navigate("all_reminders_screen") },
				icon = {
					Icon(
						imageVector = remindersIcon,
						contentDescription = "Reminders"
					)
				},
				colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
			)

			// Botón SEARCH
			NavigationBarItem(
				selected = isSearchRoute,
				onClick = { navController.navigate("search_screen") },
				icon = {
					Icon(
						imageVector = searchIcon,
						contentDescription = "Search"
					)
				},
				colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
			)
		}

		// FAB posicionado sobre la NavigationBar
		Box(
			modifier = Modifier
				.align(Alignment.Center)
				.offset(y = (-20).dp)
		) {
			FloatingActionButton(
				onClick = { showMenu = true },
				containerColor = MaterialTheme.colorScheme.primary,
				contentColor = Color.White,
				shape = CircleShape,
				modifier = Modifier.size(56.dp)
			) {
				Icon(Icons.Default.Add, contentDescription = "Add")
			}

			DropdownMenu(
				expanded = showMenu,
				onDismissRequest = { showMenu = false },
				modifier = Modifier.background(MaterialTheme.colorScheme.background)
			) {
				DropdownMenuItem(
					text = { Text("Crear Nota") },
					onClick = {
						showMenu = false
						navController.navigate("edit_note")
					},
					leadingIcon = {
						Icon(Icons.AutoMirrored.Filled.StickyNote2, contentDescription = null)
					}
				)
				DropdownMenuItem(
					text = { Text("Crear Recordatorio") },
					onClick = {
						showMenu = false
						navController.navigate("edit_note?isReminder=true")
					},
					leadingIcon = {
						Icon(Icons.Default.Notifications, contentDescription = null)
					}
				)
			}
		}
	}
}