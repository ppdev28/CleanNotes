package com.example.cleannotes.presentation.ui.edit_note

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material.icons.outlined.Draw
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FormatAlignJustify
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cleannotes.R

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    Scaffold(
        topBar = {
            EditNoteTopBar(
                title = titleState,
                onBackClick = { navController.navigateUp() },
                onShareClick = { /* Implementar compartir */ },
                onMoreClick = { /* Implementar más opciones */ }
            )
        },
        bottomBar = {
            EditNoteBottomBar(
                onListClick = { /* Implementar lista */ },
                onAttachClick = { /* Implementar adjuntar */ },
                onFormatClick = { /* Implementar formato */ },
                onDrawClick = { /* Implementar dibujo */ },
                onNewNoteClick = { /* Implementar nueva nota */ }
            )
        }
    ) { padding ->
        EditNoteContent(
            title = titleState,
            content = contentState,
            modifier = Modifier.padding(padding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteTopBar(
    title: String,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onMoreClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Atrás"
                )
            }
        },
        actions = {
            IconButton(onClick = onShareClick) {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = "Compartir"
                )
            }
            IconButton(onClick = onMoreClick) {
                Icon(
                    imageVector = Icons.Default.MoreHoriz,
                    contentDescription = "Más opciones"
                )
            }
        }
    )
}

@Composable
fun EditNoteContent(
    title: String,
    content: String,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "#school #kine210 #practicum",
            color = Color(0xFFFF9800),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Placeholder para la imagen, se debe usar una imagen real
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Diagrama",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun EditNoteBottomBar(
    onListClick: () -> Unit,
    onAttachClick: () -> Unit,
    onFormatClick: () -> Unit,
    onDrawClick: () -> Unit,
    onNewNoteClick: () -> Unit
) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = onListClick) {
                Icon(Icons.AutoMirrored.Outlined.List, contentDescription = "Lista")
            }
            IconButton(onClick = onAttachClick) {
                Icon(Icons.Outlined.AttachFile, contentDescription = "Adjuntar")
            }
            IconButton(onClick = onFormatClick) {
                Icon(Icons.Outlined.FormatAlignJustify, contentDescription = "Formato")
            }
            IconButton(onClick = onDrawClick) {
                Icon(Icons.Outlined.Draw, contentDescription = "Dibujar")
            }
            IconButton(onClick = onNewNoteClick) {
                Icon(Icons.Outlined.Edit, contentDescription = "Nueva nota")
            }
        }
    }
}