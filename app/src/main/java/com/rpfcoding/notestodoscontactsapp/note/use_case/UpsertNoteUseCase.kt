package com.rpfcoding.notestodoscontactsapp.note.use_case

import com.rpfcoding.notestodoscontactsapp.core.domain.model.Note
import com.rpfcoding.notestodoscontactsapp.core.domain.repository.NoteRepository
import com.rpfcoding.notestodoscontactsapp.core.util.InvalidNoteException
import com.rpfcoding.notestodoscontactsapp.core.util.Resource
import javax.inject.Inject

class UpsertNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note): Boolean {
        if (note.title.isBlank()) {
            throw InvalidNoteException("The title of the note cannot be empty.")
        }

        return if (note.id.isBlank()) {
            when (repository.create(note)) {
                is Resource.Error -> false
                is Resource.Success -> true
            }
        } else {
            when (repository.update(note)) {
                is Resource.Error -> false
                is Resource.Success -> true
            }
        }
    }
}
