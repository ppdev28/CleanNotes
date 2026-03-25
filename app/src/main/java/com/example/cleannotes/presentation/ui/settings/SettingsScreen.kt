package com.example.cleannotes.presentation.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SettingsScreen(
	navController: NavController
) {
	var pushNotifications by remember { mutableStateOf(false) }
	var selectedTheme by remember { mutableStateOf("Light Mode") }

	Scaffold(
		containerColor = MaterialTheme.colorScheme.background,
		modifier = Modifier.padding(top = 24.dp),
		topBar = {
			Column(
				modifier = Modifier
					.fillMaxWidth()
					.background(Color.White)
			) {
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.padding(horizontal = 24.dp, vertical = 24.dp),
					verticalAlignment = Alignment.CenterVertically
				) {
					Icon(
						imageVector = Icons.Default.Settings,
						contentDescription = "Settings",
						modifier = Modifier.size(28.dp),
						tint = Color.Black
					)
					Spacer(modifier = Modifier.width(12.dp))
					Text(
						text = "Settings",
						fontSize = 24.sp,
						fontWeight = FontWeight.Bold,
						color = Color.Black
					)
				}
				HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp)
			}
		},
		bottomBar = {
			Column(
				modifier = Modifier
					.fillMaxWidth()
					.background(Color.White)
			) {
				HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp)
				Button(
					onClick = { navController.popBackStack() },
					modifier = Modifier
						.fillMaxWidth()
						.padding(horizontal = 24.dp, vertical = 24.dp)
						.height(56.dp),
					colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
					shape = RoundedCornerShape(12.dp)
				) {
					Text(
						text = "Save Changes",
						color = Color.White,
						fontSize = 16.sp,
						fontWeight = FontWeight.Medium
					)
				}
			}
		}
	) { padding ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(padding)
				.padding(horizontal = 24.dp)
		) {
			Spacer(modifier = Modifier.height(32.dp))

			// Notifications Section
			Text(
				text = "Notifications",
				fontSize = 20.sp,
				fontWeight = FontWeight.Bold,
				color = Color.Black
			)
			Spacer(modifier = Modifier.height(16.dp))
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically
			) {
				Text(
					text = "Push Notifications",
					fontSize = 16.sp,
					color = Color.Black
				)
				// Usando un Box para simular el rectángulo de la imagen
				Box(
					modifier = Modifier
						.width(48.dp)
						.height(24.dp)
						.clip(RoundedCornerShape(4.dp))
						.border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
						.clickable { pushNotifications = !pushNotifications }
						.background(if (pushNotifications) Color.Black else Color.Transparent)
				)
			}

			Spacer(modifier = Modifier.height(48.dp))

			// Theme Section
			Text(
				text = "Theme",
				fontSize = 20.sp,
				fontWeight = FontWeight.Bold,
				color = Color.Black
			)
			Spacer(modifier = Modifier.height(8.dp))

			// Light Mode
			Row(
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier
					.fillMaxWidth()
					.clickable { selectedTheme = "Light Mode" }
					.padding(vertical = 8.dp)
			) {
				RadioButton(
					selected = selectedTheme == "Light Mode",
					onClick = { selectedTheme = "Light Mode" },
					colors = RadioButtonDefaults.colors(
						selectedColor = Color.Black,
						unselectedColor = Color.Gray
					)
				)
				Text(
					text = "Light Mode",
					fontSize = 16.sp,
					color = Color.Black
				)
			}

			// Dark Mode
			Row(
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier
					.fillMaxWidth()
					.clickable { selectedTheme = "Dark Mode" }
					.padding(vertical = 8.dp)
			) {
				RadioButton(
					selected = selectedTheme == "Dark Mode",
					onClick = { selectedTheme = "Dark Mode" },
					colors = RadioButtonDefaults.colors(
						selectedColor = Color.Black,
						unselectedColor = Color.Gray
					)
				)
				Text(
					text = "Dark Mode",
					fontSize = 16.sp,
					color = Color.Black
				)
			}

			Spacer(modifier = Modifier.height(32.dp))

			// Storage Section
			Text(
				text = "Storage",
				fontSize = 20.sp,
				fontWeight = FontWeight.Bold,
				color = Color.Black
			)
			Spacer(modifier = Modifier.height(16.dp))
			Text(
				text = "Select storage",
				fontSize = 16.sp,
				color = Color.Black,
				modifier = Modifier
					.fillMaxWidth()
					.clickable { /* Handle storage selection */ }
					.padding(vertical = 8.dp)
			)
		}
	}
}