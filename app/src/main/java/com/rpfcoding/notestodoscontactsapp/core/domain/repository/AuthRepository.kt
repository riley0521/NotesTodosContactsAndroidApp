package com.rpfcoding.notestodoscontactsapp.core.domain.repository

import com.rpfcoding.notestodoscontactsapp.core.util.Resource

interface AuthRepository {
    suspend fun login(email: String, password: String): Resource<Unit>
    suspend fun signup(email: String, password: String): Resource<Unit>
    suspend fun authenticate(): Resource<Unit>
    suspend fun signOut(): Resource<Unit>
}