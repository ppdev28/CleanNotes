package com.example.cleannotes.presentation.ui.edit_note

sealed class EditNoteEvent {
	data class EnteredTitle(val value: String) : EditNoteEvent()
	data class EnteredContent(val value: String) : EditNoteEvent()
}