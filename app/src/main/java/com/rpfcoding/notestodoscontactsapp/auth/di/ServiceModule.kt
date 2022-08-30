package com.rpfcoding.notestodoscontactsapp.auth.di

import com.rpfcoding.notestodoscontactsapp.auth.data.repository.AuthRepositoryImpl
import com.rpfcoding.notestodoscontactsapp.auth.data.repository.LoggerRepositoryImpl
import com.rpfcoding.notestodoscontactsapp.auth.data.repository.TaskRepositoryImpl
import com.rpfcoding.notestodoscontactsapp.auth.domain.repository.AuthRepository
import com.rpfcoding.notestodoscontactsapp.auth.domain.repository.LoggerRepository
import com.rpfcoding.notestodoscontactsapp.auth.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideLogService(impl: LoggerRepositoryImpl): LoggerRepository

    @Binds
    abstract fun provideStorageService(impl: TaskRepositoryImpl): TaskRepository
}