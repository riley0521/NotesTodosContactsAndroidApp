package com.rpfcoding.notestodoscontactsapp.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun saveToken(token: String)

    fun getToken(): Flow<String>
}