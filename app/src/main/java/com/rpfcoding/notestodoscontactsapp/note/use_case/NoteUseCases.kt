package com.rpfcoding.notestodoscontactsapp.note.use_case

data class NoteUseCases(
    val deleteNote: DeleteNoteUseCase,
    val getAllNotes: GetAllNotesUseCase,
    val upsertNote: UpsertNoteUseCase
)
