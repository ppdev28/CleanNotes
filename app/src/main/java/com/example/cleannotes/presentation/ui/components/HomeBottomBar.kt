package com.example.cleannotes.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeBottomBar(
	modifier: Modifier = Modifier,
	onQuickAdd: (String, Boolean) -> Unit
) {
	var text by remember { mutableStateOf("") }
	var isReminderMode by remember { mutableStateOf(false) }
	val focusManager = LocalFocusManager.current

	Column(
		modifier = modifier
			.fillMaxWidth()
			.background(Color.Transparent)
			.padding(vertical = 16.dp, horizontal = 24.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		// Little top drag handle indicator
		Box(
			modifier = Modifier
				.width(100.dp)
				.height(3.dp)
				.clip(RoundedCornerShape(50))
				.background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f))
		)
		Spacer(modifier = Modifier.height(16.dp))

		// Text field container
		Box(
			modifier = Modifier
				.fillMaxWidth()
				.height(60.dp)
				.clip(RoundedCornerShape(20.dp))
				.border(1.dp, Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
				.background(Color(0xFFF7F7F7))
				.padding(horizontal = 16.dp, vertical = 12.dp),
			contentAlignment = Alignment.CenterStart
		) {
			Row(
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier.fillMaxWidth()
			) {
				Box(modifier = Modifier.weight(1f)) {
					BasicTextField(
						value = text,
						onValueChange = { text = it },
						textStyle = TextStyle(
							fontSize = 16.sp,
							color = MaterialTheme.colorScheme.background,
							letterSpacing = 4.sp
						),
						keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
						keyboardActions = KeyboardActions(
							onDone = {
								if (text.isNotBlank()) {
									onQuickAdd(text, isReminderMode)
									text = ""
									focusManager.clearFocus()
								}
							}
						),
						modifier = Modifier.fillMaxWidth()
					)
					if (text.isEmpty()) {
						Text(
							text = "w r i t e . . .",
							color = Color.Gray,
							fontSize = 16.sp,
							letterSpacing = 4.sp
						)
					}
				}

				IconButton(
					onClick = { isReminderMode = !isReminderMode },
					modifier = Modifier.padding(start = 8.dp)
				) {
					Icon(
						imageVector = if (isReminderMode) Icons.Filled.Notifications else Icons.Outlined.NotificationsNone,
						contentDescription = "Toggle Reminder",
						tint = Color.Black
					)
				}
			}
		}
	}
}