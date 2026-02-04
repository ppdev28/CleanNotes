package com.example.cleannotes.presentation.ui.edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleannotes.domain.model.InvalidNoteException
import com.example.cleannotes.domain.model.Note
import com.example.cleannotes.domain.usecase.GetNoteUseCase
import com.example.cleannotes.domain.usecase.AddNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val getNoteUseCase: GetNoteUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    savedStateHandle: SavedStateHandle // Para recuperar el ID de la nota al editar
) : ViewModel() {

    // ESTADOS: Título, Contenido, Color
    private val _noteTitle = mutableStateOf("")
    val noteTitle: State<String> = _noteTitle

    private val _noteContent = mutableStateOf("")
    val noteContent: State<String> = _noteContent

    // EVENTOS: Para avisar a la UI (ej: "Guardado OK" o "Error")
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        // Al iniciar, comprobamos si nos pasaron un noteId por navegación
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    getNoteUseCase(noteId).also { note ->
                        currentNoteId = note?.id
                        _noteTitle.value = note?.title.toString()
                        _noteContent.value = note?.content.toString()
                    }
                }
            }
        }
    }

    // Funciones para cambiar los valores desde la UI
    fun onTitleChange(text: String) {
        _noteTitle.value = text
    }

    fun onContentChange(text: String) {
        _noteContent.value = text
    }

    fun saveNote() {
        viewModelScope.launch {
            try {
                addNoteUseCase(
                    Note(
                        title = noteTitle.value,
                        content = noteContent.value,
                        timestamp = System.currentTimeMillis(),
                        id = currentNoteId
                    )
                )
                // Emitimos evento de éxito
                _eventFlow.emit(UiEvent.SaveNote)
            } catch (e: InvalidNoteException) {
                // Emitimos evento de error (Snackbar)
                _eventFlow.emit(UiEvent.ShowSnackbar(
                    message = e.message ?: "No se pudo guardar la nota"
                ))
            }
        }
    }

    // Eventos sellados para comunicar ViewModel -> UI
    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }
}