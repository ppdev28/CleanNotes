package com.example.cleannotes.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleannotes.domain.usecase.GetNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase
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
}