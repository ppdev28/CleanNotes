package com.example.cleannotes.presentation.ui.edit_note

sealed class AddEditNoteEvent {
	data class EnteredTitle(val value: String) : AddEditNoteEvent()
	data class EnteredContent(val value: String) : AddEditNoteEvent()
}