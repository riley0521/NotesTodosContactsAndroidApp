package com.rpfcoding.notestodoscontactsapp.core.domain.repository

import com.rpfcoding.notestodoscontactsapp.core.domain.model.Task
import com.rpfcoding.notestodoscontactsapp.core.util.Resource

interface TaskRepository {
    suspend fun getAll(): Resource<List<Task>>
    suspend fun create(task: Task): Resource<Unit>
    suspend fun update(task: Task): Resource<Unit>
    suspend fun delete(taskId: String): Resource<Unit>
    suspend fun deleteAll(): Resource<Unit>
}