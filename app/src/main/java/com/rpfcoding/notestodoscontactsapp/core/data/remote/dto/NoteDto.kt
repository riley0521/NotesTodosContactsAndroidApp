package com.rpfcoding.notestodoscontactsapp.core.data.remote.dto

import com.rpfcoding.notestodoscontactsapp.core.domain.model.Note

data class NoteDto(
    val title: String,
    val description: String,
    val color: Int,
    val email: String,
    val createdAt: Long,
    val id: String = ""
) {
    fun toNote(): Note {
        return Note(
            title = title,
            description = description,
            color = color,
            createdAt = createdAt,
            id = id
        )
    }
}
