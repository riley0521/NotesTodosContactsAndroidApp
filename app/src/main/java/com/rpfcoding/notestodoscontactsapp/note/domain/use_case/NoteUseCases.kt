package com.rpfcoding.notestodoscontactsapp.note.domain.use_case

data class NoteUseCases(
    val deleteNote: DeleteNoteUseCase,
    val getAllNotes: GetAllNotesUseCase,
    val insertNote: InsertNoteUseCase
)
