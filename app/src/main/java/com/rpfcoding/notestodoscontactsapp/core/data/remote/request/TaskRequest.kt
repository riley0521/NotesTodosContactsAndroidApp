package com.rpfcoding.notestodoscontactsapp.core.data.remote.request

data class TaskRequest(
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
    val completed: Boolean
)
