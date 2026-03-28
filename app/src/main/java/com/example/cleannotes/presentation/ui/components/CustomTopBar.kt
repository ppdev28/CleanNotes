package com.example.cleannotes.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.automirrored.outlined.StickyNote2
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CustomTopBar(
	navController: NavController,
	isListView: Boolean? = null,
	onToggleView: (() -> Unit)? = null
) {
	Surface(
		modifier = Modifier.fillMaxWidth(),
		color = MaterialTheme.colorScheme.background,
		shadowElevation = 2.dp
	) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(end = 16.dp, start = 16.dp, top = 24.dp, bottom = 8.dp)
		) {
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(top = 24.dp),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				// Contenedor a la izquierda con el icono y el título juntos
				Row(verticalAlignment = Alignment.CenterVertically) {
					Icon(
						imageVector = Icons.AutoMirrored.Outlined.StickyNote2,
						contentDescription = "Sticky Note",
						tint = MaterialTheme.colorScheme.onSurfaceVariant,
						modifier = Modifier.size(40.dp)
					)

					Spacer(modifier = Modifier.width(8.dp))

					Text(
						text = "Clean Notes",
						fontSize = 24.sp,
						fontWeight = FontWeight.Thin
					)
				}

				// Acciones (Botones de vista) y Configuración a la derecha
				Row(verticalAlignment = Alignment.CenterVertically) {
					if (isListView != null && onToggleView != null) {
						IconButton(onClick = onToggleView) {
							Icon(
								imageVector = if (isListView) Icons.Default.GridView else Icons.AutoMirrored.Filled.ViewList,
								contentDescription = "Toggle View",
								tint = MaterialTheme.colorScheme.onSurfaceVariant
							)
						}
						Spacer(modifier = Modifier.width(8.dp))
					}

					Icon(
						imageVector = Icons.Default.Settings,
						contentDescription = "Settings",
						tint = MaterialTheme.colorScheme.onSurfaceVariant,
						modifier = Modifier
							.size(24.dp)
							.clickable {
								navController.navigate("settings_screen")
							}
					)
				}
			}
		}
	}
}