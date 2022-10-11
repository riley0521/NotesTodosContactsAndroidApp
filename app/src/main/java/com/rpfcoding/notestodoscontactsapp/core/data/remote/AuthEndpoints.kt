package com.rpfcoding.notestodoscontactsapp.core.data.remote

import com.rpfcoding.notestodoscontactsapp.core.data.remote.request.AuthRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthEndpoints {

    @POST("signup")
    suspend fun signUp(
        @Body request: AuthRequest
    )

    @POST("login")
    suspend fun login(
        @Body request: AuthRequest
    ): String

    @POST("authenticate")
    suspend fun authenticate(
        @Header("Authorization") token: String
    )
}