package com.rpfcoding.notestodoscontactsapp.core.domain.repository

import com.rpfcoding.notestodoscontactsapp.core.domain.model.Note
import com.rpfcoding.notestodoscontactsapp.core.util.NoteOrder
import com.rpfcoding.notestodoscontactsapp.core.util.Resource

interface NoteRepository {

    suspend fun create(note: Note): Resource<Unit>

    suspend fun update(note: Note): Resource<Unit>

    suspend fun getAll(): Resource<List<Note>>

    suspend fun deleteById(id: String): Resource<Unit>

    suspend fun deleteAll(): Resource<Unit>
}