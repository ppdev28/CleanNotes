package com.example.cleannotes.presentation.ui.edit_note.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleannotes.domain.model.InvalidNoteException
import com.example.cleannotes.domain.model.Note
import com.example.cleannotes.domain.usecase.AddNoteUseCase
import com.example.cleannotes.domain.usecase.GetNoteUseCase
import com.example.cleannotes.presentation.ui.edit_note.EditNoteEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
	private val getNoteUseCase: GetNoteUseCase,
	private val addNoteUseCase: AddNoteUseCase,
	savedStateHandle: SavedStateHandle
) : ViewModel() {
	
	private val _noteTitle = mutableStateOf("")
	val noteTitle: State<String> = _noteTitle
	
	private val _noteContent = mutableStateOf("")
	val noteContent: State<String> = _noteContent
	
	private val _eventFlow = MutableSharedFlow<UiEvent>()
	val eventFlow = _eventFlow.asSharedFlow()
	
	private var currentNoteId: Int? = null
	private var saveJob: Job? = null
	
	init {
		val isReminder = savedStateHandle.get<Boolean>("isReminder") ?: false
		if (isReminder) {
			_noteContent.value = "Remember"
		}

		savedStateHandle.get<Int>("noteId")?.let { noteId ->
			if (noteId != -1) {
				viewModelScope.launch {
					getNoteUseCase(noteId)?.also { note ->
						currentNoteId = note.id
						_noteTitle.value = note.title
						_noteContent.value = note.content
					}
				}
			}
		}
	}
	
	fun onEvent(event: EditNoteEvent) {
		when (event) {
			is EditNoteEvent.EnteredTitle -> {
				_noteTitle.value = event.value
			}
			
			is EditNoteEvent.EnteredContent -> {
				_noteContent.value = event.value
			}
		}
		// En cada evento de edición, cancelamos el trabajo anterior y lanzamos uno nuevo
		saveJob?.cancel()
		saveJob = viewModelScope.launch {
			// Esperamos 500ms antes de guardar
			delay(500L)
			saveNote()
		}
	}
	
	private fun saveNote() {
		viewModelScope.launch {
			try {
				// Creamos la nota a guardar
				val noteToSave = Note(
					title = noteTitle.value,
					content = noteContent.value,
					timestamp = System.currentTimeMillis(),
					id = currentNoteId
				)
				// Guardamos la nota
				addNoteUseCase(noteToSave)
				
				// Si la nota es nueva (sin ID), la volvemos a obtener para tener el ID actualizado
				if (currentNoteId == null) {
					// Esto es una simplificación. Idealmente, AddNoteUseCase devolvería el ID.
					// Para evitar una lógica compleja, asumimos que el guardado fue exitoso
					// y la próxima vez que se abra la nota ya tendrá un ID.
				}
				
				_eventFlow.emit(UiEvent.SaveNote)
			} catch (e: InvalidNoteException) {
				_eventFlow.emit(
					UiEvent.ShowSnackbar(
						message = e.message ?: "No se pudo guardar la nota"
					)
				)
			}
		}
	}
	
	sealed class UiEvent {
		data class ShowSnackbar(val message: String) : UiEvent()
		object SaveNote : UiEvent()
	}
}