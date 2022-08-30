package com.rpfcoding.notestodoscontactsapp.note.data.repository

import com.rpfcoding.notestodoscontactsapp.note.data.local.dao.NoteDao
import com.rpfcoding.notestodoscontactsapp.note.data.local.entity.NoteEntity
import com.rpfcoding.notestodoscontactsapp.note.domain.model.Note
import com.rpfcoding.notestodoscontactsapp.note.domain.repository.NoteRepository
import com.rpfcoding.notestodoscontactsapp.note.domain.util.NoteOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override suspend fun insert(note: Note) {
        noteDao.insert(
            NoteEntity(
                title = note.title,
                description = note.description,
                color = note.color,
                id = note.id.ifBlank { UUID.randomUUID().toString() }
            )
        )
    }

    override fun getAll(order: NoteOrder): Flow<List<Note>> {
        return noteDao.getNotes(order).map {
            it.map { entity ->
                entity.toNote()
            }
        }
    }

    override suspend fun delete(id: String) {
        noteDao.deleteById(id)
    }
}