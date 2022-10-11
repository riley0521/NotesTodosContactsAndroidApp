package com.rpfcoding.notestodoscontactsapp.core.data.remote.dto

import com.rpfcoding.notestodoscontactsapp.core.domain.model.Task

data class TaskDto(
    val title: String,
    /**
     * 0 - None
     * 1 - Low
     * 2 - Medium
     * 3 - High
     */
    val priority: Int,
    val dueDate: Long,
    val description: String,
    val flag: Boolean,
    val email: String,
    val completed: Boolean = false,
    val createdAt: Long,
    val id: String
) {
    fun toTask(): Task {
        return Task(
            title = title,
            priority = priority,
            dueDate = dueDate,
            description = description,
            flag = flag,
            completed = completed,
            id = id
        )
    }
}
