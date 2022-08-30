package com.rpfcoding.notestodoscontactsapp.note.domain.use_case

import com.rpfcoding.notestodoscontactsapp.note.domain.model.InvalidNoteException
import com.rpfcoding.notestodoscontactsapp.note.domain.model.Note
import com.rpfcoding.notestodoscontactsapp.note.domain.repository.NoteRepository
import javax.inject.Inject

class InsertNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if(note.title.isBlank()) {
            throw InvalidNoteException("The title of the note cannot be empty.")
        }

        repository.insert(note)
    }
}
