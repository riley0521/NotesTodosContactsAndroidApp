package com.rpfcoding.notestodoscontactsapp.core.di

import android.content.Context
import androidx.room.Room
import com.rpfcoding.notestodoscontactsapp.core.data.local.MyDatabase
import com.rpfcoding.notestodoscontactsapp.core.data.remote.AuthEndpoints
import com.rpfcoding.notestodoscontactsapp.core.data.remote.NoteEndpoints
import com.rpfcoding.notestodoscontactsapp.core.data.remote.TaskEndpoints
import com.rpfcoding.notestodoscontactsapp.core.data.repository.NoteRepositoryImpl
import com.rpfcoding.notestodoscontactsapp.core.data.repository.UserRepositoryImpl
import com.rpfcoding.notestodoscontactsapp.core.domain.repository.NoteRepository
import com.rpfcoding.notestodoscontactsapp.core.domain.repository.UserRepository
import com.rpfcoding.notestodoscontactsapp.core.util.Constants
import com.rpfcoding.notestodoscontactsapp.core.util.NetworkConnectivityObserver
import com.rpfcoding.notestodoscontactsapp.note.use_case.DeleteNoteUseCase
import com.rpfcoding.notestodoscontactsapp.note.use_case.GetAllNotesUseCase
import com.rpfcoding.notestodoscontactsapp.note.use_case.UpsertNoteUseCase
import com.rpfcoding.notestodoscontactsapp.note.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMyDatabase(
        @ApplicationContext ctx: Context
    ) = Room.databaseBuilder(
        ctx,
        MyDatabase::class.java,
        "my_db_bitch"
    ).build()

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .callTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.Urls.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthEndpoints(
        retrofit: Retrofit
    ): AuthEndpoints = retrofit.create()

    @Provides
    @Singleton
    fun provideNoteEndpoints(
        retrofit: Retrofit
    ): NoteEndpoints = retrofit.create()

    @Provides
    @Singleton
    fun provideTaskEndpoints(
        retrofit: Retrofit
    ): TaskEndpoints = retrofit.create()

    @Provides
    @Singleton
    fun provideNoteUseCases(
        noteRepository: NoteRepository
    ) = NoteUseCases(
        deleteNote = DeleteNoteUseCase(noteRepository),
        getAllNotes = GetAllNotesUseCase(noteRepository),
        upsertNote = UpsertNoteUseCase(noteRepository)
    )

    @Provides
    @Singleton
    fun provideUserRepository(
        @ApplicationContext ctx: Context
    ): UserRepository = UserRepositoryImpl(ctx)

    @Provides
    @Singleton
    fun provideNetworkConnectivityObserver(
        @ApplicationContext ctx: Context
    ): NetworkConnectivityObserver = NetworkConnectivityObserver(ctx)
}