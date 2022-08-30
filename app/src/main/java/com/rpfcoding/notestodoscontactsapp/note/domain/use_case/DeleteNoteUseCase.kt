package com.rpfcoding.notestodoscontactsapp.note.domain.use_case

import com.rpfcoding.notestodoscontactsapp.note.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: String) {
        repository.delete(id)
    }
}