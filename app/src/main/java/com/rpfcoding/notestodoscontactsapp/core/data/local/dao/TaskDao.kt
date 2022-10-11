package com.rpfcoding.notestodoscontactsapp.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rpfcoding.notestodoscontactsapp.core.domain.model.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(task: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(tasks: List<Task>)

    @Query("DELETE FROM tbl_tasks WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM tbl_tasks")
    suspend fun deleteAll()

    @Query("SELECT * FROM tbl_tasks")
    suspend fun getAll(): List<Task>
}