package com.example.cleannotes.presentation.ui.edit_note.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleannotes.domain.usecase.AddNoteUseCase
import com.example.cleannotes.domain.usecase.GetNoteUseCase
import com.example.cleannotes.presentation.ui.edit_note.AddEditNoteEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
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
	
	init {
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
	
	fun onEvent(event: AddEditNoteEvent) {
		when (event) {
			is AddEditNoteEvent.EnteredTitle -> {
				_noteTitle.value = event.value
			}
			
			is AddEditNoteEvent.EnteredContent -> {
				_noteContent.value = event.value
			}
			
		}
	}
	
	sealed class UiEvent {
		data class ShowSnackbar(val message: String) : UiEvent()
		object SaveNote : UiEvent()
	}
}