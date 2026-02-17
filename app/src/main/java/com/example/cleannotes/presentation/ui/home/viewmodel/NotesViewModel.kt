package com.example.cleannotes.presentation.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleannotes.domain.model.InvalidNoteException
import com.example.cleannotes.domain.model.Note
import com.example.cleannotes.domain.usecase.AddNoteUseCase
import com.example.cleannotes.domain.usecase.GetNotesUseCase
import com.example.cleannotes.presentation.NotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
	private val getNotesUseCase: GetNotesUseCase,
	private val addNoteUseCase: AddNoteUseCase
) : ViewModel() {
	
	// Estado mutable interno (solo el VM lo modifica)
	private val _state = MutableStateFlow(NotesState())
	
	// Estado inmutable expuesto a la UI (la UI solo lee)
	val state: StateFlow<NotesState> = _state.asStateFlow()
	
	init {
		// Al iniciarse el ViewModel, cargamos las notas
		getNotes()
	}
	
	private fun getNotes() {
		// Llamamos al caso de uso (invoke operator)
		getNotesUseCase()
			.onEach { notes ->
				// Cada vez que la BD cambie, actualizamos el estado
				_state.value = state.value.copy(
					notes = notes,
					isLoading = false
				)
			}
			.launchIn(viewModelScope) // Ejecutamos en el scope del ViewModel
	}
	
	// --- NUEVA FUNCIÓN PARA LA BARRA RÁPIDA ---
	fun onQuickAdd(text: String, isReminder: Boolean) {
		viewModelScope.launch {
			if (text.isBlank()) return@launch
			
			// COLORES: Blanco (Nota normal) vs Amarillo Pastel (Recordatorio)
			// 0xFFFFFFFF es Blanco Puro
			// 0xFFFFF59D es Amarillo "Post-it"
			val color = if (isReminder) 0xFFFFF59D.toInt() else 0xFFFFFFFF.toInt()
			
			val newNote = Note(
				title = text,
				content = if (isReminder) "Remember" else "To Note", // Texto auxiliar opcional
				timestamp = System.currentTimeMillis()
			)
			
			try {
				// 👇 AQUÍ ESTABA EL ERROR:
				// NO uses mayúscula 'AddNoteUseCase(newNote)' (eso llama al constructor)
				// USA la variable inyectada 'addNoteUseCase(newNote)'
				addNoteUseCase(newNote)
				
			} catch (e: InvalidNoteException) {
				// Podrías emitir un error a la UI si quieres
			}
		}
	}
}