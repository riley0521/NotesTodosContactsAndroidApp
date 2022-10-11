package com.rpfcoding.notestodoscontactsapp.note.presentation.note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpfcoding.notestodoscontactsapp.core.domain.model.Note
import com.rpfcoding.notestodoscontactsapp.core.domain.repository.UserRepository
import com.rpfcoding.notestodoscontactsapp.note.use_case.NoteUseCases
import com.rpfcoding.notestodoscontactsapp.core.util.NoteOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val userRepository: UserRepository
) : ViewModel() {

    var state by mutableStateOf(NotesState())
        private set

    private var recentlyDeletedNote: Note? = null

    init {
        fetchNotes(state.noteOrder)
    }

    private fun fetchNotes(order: NoteOrder) {
        viewModelScope.launch {
            if(userRepository.getToken().first().isBlank()) {
                // TODO: Send channel to inform user it needs to sign in.
            } else {
                state = state.copy(
                    notes = noteUseCases.getAllNotes(order)
                )
            }
        }
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    if(noteUseCases.deleteNote(event.note.id)) {
                        recentlyDeletedNote = event.note
                        fetchNotes(state.noteOrder)
                    }
                }
            }
            is NotesEvent.Order -> {
                if (state.noteOrder::class == event.order::class &&
                    state.noteOrder.orderType == event.order.orderType
                ) {
                    return
                }

                fetchNotes(event.order)
            }
            NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    if(noteUseCases.upsertNote(recentlyDeletedNote ?: return@launch)) {
                        recentlyDeletedNote = null
                        fetchNotes(state.noteOrder)
                    }
                }
            }
            NotesEvent.ToggleOrderSection -> {
                state = state.copy(isOrderSectionVisible = !state.isOrderSectionVisible)
            }
        }
    }

    sealed class NotesEvent {
        data class Order(val order: NoteOrder) : NotesEvent()
        data class DeleteNote(val note: Note) : NotesEvent()
        object RestoreNote : NotesEvent()
        object ToggleOrderSection : NotesEvent()
    }
}