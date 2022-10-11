package com.rpfcoding.notestodoscontactsapp.note.use_case

import com.rpfcoding.notestodoscontactsapp.core.domain.repository.NoteRepository
import com.rpfcoding.notestodoscontactsapp.core.util.Resource
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: String): Boolean {
        return when(repository.deleteById(id)) {
            is Resource.Error -> false
            is Resource.Success -> true
        }
    }
}