package com.rpfcoding.notestodoscontactsapp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rpfcoding.notestodoscontactsapp.core.data.local.dao.NoteDao
import com.rpfcoding.notestodoscontactsapp.core.data.local.dao.TaskDao
import com.rpfcoding.notestodoscontactsapp.core.domain.model.Note
import com.rpfcoding.notestodoscontactsapp.core.domain.model.Task

@Database(
    entities = [
        Note::class,
        Task::class
    ],
    version = 1,
    exportSchema = true
)
abstract class MyDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao
    abstract val taskDao: TaskDao
}