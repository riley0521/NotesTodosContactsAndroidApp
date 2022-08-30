package com.rpfcoding.notestodoscontactsapp.note.di

import android.content.Context
import androidx.room.Room
import com.rpfcoding.notestodoscontactsapp.note.data.local.NoteDatabase
import com.rpfcoding.notestodoscontactsapp.note.data.local.dao.NoteDao
import com.rpfcoding.notestodoscontactsapp.note.data.repository.NoteRepositoryImpl
import com.rpfcoding.notestodoscontactsapp.note.domain.repository.NoteRepository
import com.rpfcoding.notestodoscontactsapp.note.domain.use_case.DeleteNoteUseCase
import com.rpfcoding.notestodoscontactsapp.note.domain.use_case.GetAllNotesUseCase
import com.rpfcoding.notestodoscontactsapp.note.domain.use_case.InsertNoteUseCase
import com.rpfcoding.notestodoscontactsapp.note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotesModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(
        @ApplicationContext ctx: Context
    ): NoteDatabase {
        return Room.databaseBuilder(
            ctx,
            NoteDatabase::class.java,
            "note_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(
        db: NoteDatabase
    ): NoteDao = db.noteDao()

    @Provides
    @Singleton
    fun provideNoteRepository(
        noteDao: NoteDao
    ): NoteRepository = NoteRepositoryImpl(noteDao)

    @Provides
    @Singleton
    fun provideNoteUseCases(
        noteRepository: NoteRepository
    ): NoteUseCases {
        return NoteUseCases(
            DeleteNoteUseCase(noteRepository),
            GetAllNotesUseCase(noteRepository),
            InsertNoteUseCase(noteRepository)
        )
    }
}