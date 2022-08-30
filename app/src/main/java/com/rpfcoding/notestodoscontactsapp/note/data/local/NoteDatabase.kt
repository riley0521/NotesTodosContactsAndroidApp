package com.rpfcoding.notestodoscontactsapp.note.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rpfcoding.notestodoscontactsapp.note.data.local.dao.NoteDao
import com.rpfcoding.notestodoscontactsapp.note.data.local.entity.NoteEntity

@Database(
    entities = [
        NoteEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
}