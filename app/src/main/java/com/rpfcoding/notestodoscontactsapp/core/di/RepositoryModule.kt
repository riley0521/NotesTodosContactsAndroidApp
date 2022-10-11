package com.rpfcoding.notestodoscontactsapp.core.di

import com.rpfcoding.notestodoscontactsapp.core.data.repository.AuthRepositoryImpl
import com.rpfcoding.notestodoscontactsapp.core.data.repository.NoteRepositoryImpl
import com.rpfcoding.notestodoscontactsapp.core.data.repository.TaskRepositoryImpl
import com.rpfcoding.notestodoscontactsapp.core.domain.repository.AuthRepository
import com.rpfcoding.notestodoscontactsapp.core.domain.repository.NoteRepository
import com.rpfcoding.notestodoscontactsapp.core.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindTaskRepository(impl: TaskRepositoryImpl): TaskRepository

    @Binds
    abstract fun bindNoteRepository(impl: NoteRepositoryImpl): NoteRepository

}