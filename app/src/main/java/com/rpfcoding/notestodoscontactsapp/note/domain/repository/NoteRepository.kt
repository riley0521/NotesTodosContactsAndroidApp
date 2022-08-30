package com.rpfcoding.notestodoscontactsapp.note.domain.repository

import com.rpfcoding.notestodoscontactsapp.note.domain.model.Note
import com.rpfcoding.notestodoscontactsapp.note.domain.util.NoteOrder
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun insert(note: Note)

    fun getAll(order: NoteOrder): Flow<List<Note>>

    suspend fun delete(id: String)
}