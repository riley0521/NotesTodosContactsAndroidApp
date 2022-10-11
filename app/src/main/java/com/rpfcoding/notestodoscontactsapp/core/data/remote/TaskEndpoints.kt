package com.rpfcoding.notestodoscontactsapp.core.data.remote

import com.rpfcoding.notestodoscontactsapp.core.data.remote.dto.TaskDto
import com.rpfcoding.notestodoscontactsapp.core.data.remote.request.TaskRequest
import retrofit2.http.*

sealed interface TaskEndpoints {
    @GET("tasks")
    suspend fun getAll(
        @Header("Authorization") token: String
    ): List<TaskDto>

    @POST("tasks")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body task: TaskRequest
    ): String

    @PUT("tasks/{id}")
    suspend fun update(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body task: TaskRequest
    )

    @DELETE("tasks/{id}")
    suspend fun delete(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    )

    @DELETE("tasks")
    suspend fun deleteAll(
        @Header("Authorization") token: String
    )
}