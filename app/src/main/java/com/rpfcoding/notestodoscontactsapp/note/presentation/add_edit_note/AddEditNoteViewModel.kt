package com.rpfcoding.notestodoscontactsapp.note.presentation.add_edit_note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpfcoding.notestodoscontactsapp.R
import com.rpfcoding.notestodoscontactsapp.core.domain.model.Note
import com.rpfcoding.notestodoscontactsapp.core.util.Constants
import com.rpfcoding.notestodoscontactsapp.core.util.InvalidNoteException
import com.rpfcoding.notestodoscontactsapp.core.util.UiText
import com.rpfcoding.notestodoscontactsapp.note.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var noteTitle by mutableStateOf(
        NoteTextFieldState(
            hint = UiText.StringResource(resId = R.string.hint_title)
        )
    )
        private set

    var noteDescription by mutableStateOf(
        NoteTextFieldState(
            hint = UiText.StringResource(resId = R.string.hint_description)
        )
    )
        private set

    var noteColor by mutableStateOf(Constants.noteColors.random().toArgb())
        private set

    private val _eventChannel = Channel<UiEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        fetchSelectedNote()
    }

    private fun fetchSelectedNote() {
        val note = savedStateHandle.get<Note>("note")
        note?.let {
            noteTitle = noteTitle.copy(
                text = note.title,
                hint = UiText.StringResource(resId = R.string.hint_title)
            )

            noteDescription = noteDescription.copy(
                text = note.description,
                hint = UiText.StringResource(resId = R.string.hint_description)
            )

            noteColor = note.color
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.ChangeColor -> {
                noteColor = event.color
            }
            is AddEditNoteEvent.ChangeDescriptionFocus -> {
                noteDescription = noteDescription.copy(
                    isHintVisible = !event.focusState.isFocused && noteDescription.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                noteTitle = noteTitle.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitle.text.isBlank()
                )
            }
            is AddEditNoteEvent.EnteredDescription -> {
                noteDescription = noteDescription.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.EnteredTitle -> {
                noteTitle = noteTitle.copy(
                    text = event.value
                )
            }
            AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        val note = savedStateHandle.get<Note>("note")

                        noteUseCases.upsertNote(
                            Note(
                                title = noteTitle.text,
                                description = noteDescription.text,
                                color = noteColor,
                                id = note?.id ?: ""
                            )
                        )

                        _eventChannel.send(
                            UiEvent.SuccessSaveNote
                        )
                    } catch (e: InvalidNoteException) {
                        if (e.message != null) {
                            _eventChannel.send(
                                UiEvent.ShowErrorMessage(UiText.DynamicString(e.message))
                            )
                        } else {
                            _eventChannel.send(
                                UiEvent.ShowErrorMessage(UiText.StringResource(resId = R.string.unknown_error))
                            )
                        }
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowErrorMessage(val message: UiText) : UiEvent()
        object SuccessSaveNote : UiEvent()
    }

    sealed class AddEditNoteEvent {
        data class EnteredTitle(val value: String) : AddEditNoteEvent()
        data class ChangeTitleFocus(val focusState: FocusState) : AddEditNoteEvent()
        data class EnteredDescription(val value: String) : AddEditNoteEvent()
        data class ChangeDescriptionFocus(val focusState: FocusState) : AddEditNoteEvent()
        data class ChangeColor(val color: Int) : AddEditNoteEvent()
        object SaveNote : AddEditNoteEvent()
    }
}