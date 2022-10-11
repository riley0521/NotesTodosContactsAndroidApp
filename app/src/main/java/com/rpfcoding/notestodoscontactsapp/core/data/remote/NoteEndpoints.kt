package com.rpfcoding.notestodoscontactsapp.core.data.remote

import com.rpfcoding.notestodoscontactsapp.core.data.remote.dto.NoteDto
import com.rpfcoding.notestodoscontactsapp.core.data.remote.request.NoteRequest
import retrofit2.http.*

interface NoteEndpoints {

    @GET("notes")
    suspend fun getAll(
        @Header("Authorization") token: String
    ): List<NoteDto>

    @POST("notes")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body note: NoteRequest
    ): String

    @PUT("notes/{id}")
    suspend fun update(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body note: NoteRequest
    )

    @DELETE("notes/{id}")
    suspend fun delete(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    )

    @DELETE("notes")
    suspend fun deleteAll(
        @Header("Authorization") token: String
    )
}