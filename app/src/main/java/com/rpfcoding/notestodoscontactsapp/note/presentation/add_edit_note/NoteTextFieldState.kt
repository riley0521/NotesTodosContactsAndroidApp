package com.rpfcoding.notestodoscontactsapp.note.presentation.add_edit_note

import com.rpfcoding.notestodoscontactsapp.core.util.UiText

data class NoteTextFieldState(
    val text: String = "",
    val hint: UiText? = null,
    val isHintVisible: Boolean = true
)
