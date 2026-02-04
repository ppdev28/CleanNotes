package com.example.cleannotes.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun HomeBottomBar(
    modifier: Modifier = Modifier,
    onQuickAdd: (String, Boolean) -> Unit // Callback: (Texto, esRecordatorio)
) {
    var text by remember { mutableStateOf("") }
    var isReminderMode by remember { mutableStateOf(false) } // false = Nota, true = Recordatorio
    val focusManager = LocalFocusManager.current

    // Contenedor flotante con sombra
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp)), // Sombra suave
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFF5F5F5) // Fondo gris muy claro como en la imagen
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Campo de texto transparente
            TextField(
                value = text,
                onValueChange = { text = it },
                placeholder = { Text("write...", color = Color.Gray) },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent, // Sin línea abajo
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (text.isNotBlank()) {
                            onQuickAdd(text, isReminderMode)
                            text = "" // Limpiar campo
                            focusManager.clearFocus() // Ocultar teclado
                        }
                    }
                )
            )

            // Icono de Campana (Toggle)
            IconButton(
                onClick = { isReminderMode = !isReminderMode }
            ) {
                Icon(
                    imageVector = if (isReminderMode) Icons.Filled.Notifications else Icons.Outlined.Notifications,
                    contentDescription = "Toggle Reminder",
                    // Si está activo (relleno), lo ponemos negro, si no, gris
                    tint = if (isReminderMode) Color.Black else Color.Gray
                )
            }
        }
    }
}