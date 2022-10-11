package com.rpfcoding.notestodoscontactsapp.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rpfcoding.notestodoscontactsapp.core.domain.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(notes: List<Note>)

    @Query("DELETE FROM tbl_notes WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM tbl_notes")
    suspend fun deleteAll()

    @Query("SELECT * FROM tbl_notes")
    suspend fun getAll(): List<Note>
}