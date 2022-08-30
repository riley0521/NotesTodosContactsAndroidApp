package com.rpfcoding.notestodoscontactsapp.note.domain.use_case

import com.rpfcoding.notestodoscontactsapp.note.domain.model.Note
import com.rpfcoding.notestodoscontactsapp.note.domain.repository.NoteRepository
import com.rpfcoding.notestodoscontactsapp.note.domain.util.NoteOrder
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(order: NoteOrder): Flow<List<Note>> = repository.getAll(order)
}