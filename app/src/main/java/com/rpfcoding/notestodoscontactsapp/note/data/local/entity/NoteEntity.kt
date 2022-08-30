package com.rpfcoding.notestodoscontactsapp.note.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rpfcoding.notestodoscontactsapp.note.domain.model.Note
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "tbl_notes")
data class NoteEntity(
    val title: String,
    val description: String,
    val color: Int,
    val createdAt: Long = System.currentTimeMillis(),
    @PrimaryKey
    val id: String = UUID.randomUUID().toString()
) {
    fun toNote(): Note {
        return Note(
            title = title,
            description = description,
            color = color,
            createdAt = SimpleDateFormat("MM/dd/yyyy").format(Date(createdAt)),
            id = id
        )
    }
}
