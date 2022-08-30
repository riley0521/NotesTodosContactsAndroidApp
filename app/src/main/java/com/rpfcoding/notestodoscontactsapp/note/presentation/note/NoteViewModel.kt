package com.rpfcoding.notestodoscontactsapp.note.presentation.note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpfcoding.notestodoscontactsapp.note.domain.model.Note
import com.rpfcoding.notestodoscontactsapp.note.domain.use_case.NoteUseCases
import com.rpfcoding.notestodoscontactsapp.note.domain.util.NoteOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    var state by mutableStateOf(NotesState())
        private set

    private var recentlyDeletedNote: Note? = null

    init {
        fetchNotes(state.noteOrder)
    }

    private fun fetchNotes(order: NoteOrder) {
        viewModelScope.launch {
            noteUseCases.getAllNotes(order)
                .onEach { notes ->
                    state = state.copy(
                        notes = notes,
                        noteOrder = order
                    )
                }.launchIn(viewModelScope)
        }
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note.id)
                    recentlyDeletedNote = event.note
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
                    noteUseCases.insertNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
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