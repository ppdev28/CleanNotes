package com.example.cleannotes.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CustomTopBar(navController: NavController) {
	Surface(
		modifier = Modifier.fillMaxWidth(),
		color = MaterialTheme.colorScheme.background,
		shadowElevation = 2.dp// The image background is white
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
				Text(
					text = "Clean Notes",
					fontSize = 24.sp,
					style = MaterialTheme.typography.titleLarge
				)
				// Profile Icon
				Icon(
					imageVector = Icons.Default.Person,
					contentDescription = "Profile",
					tint = MaterialTheme.colorScheme.onSurfaceVariant,
					modifier = Modifier
						.size(40.dp)
						.clip(CircleShape)
						.border(2.dp, Color.Black, CircleShape)
						.clickable {
							navController.navigate("profile_screen")
						}
				)
			}
		}
	}
}