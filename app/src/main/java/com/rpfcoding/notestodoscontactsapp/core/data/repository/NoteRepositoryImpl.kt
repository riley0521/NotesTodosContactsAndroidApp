package com.rpfcoding.notestodoscontactsapp.core.data.repository

import com.rpfcoding.notestodoscontactsapp.core.data.local.MyDatabase
import com.rpfcoding.notestodoscontactsapp.core.data.remote.NoteEndpoints
import com.rpfcoding.notestodoscontactsapp.core.data.remote.request.NoteRequest
import com.rpfcoding.notestodoscontactsapp.core.domain.model.Note
import com.rpfcoding.notestodoscontactsapp.core.domain.repository.NoteRepository
import com.rpfcoding.notestodoscontactsapp.core.domain.repository.UserRepository
import com.rpfcoding.notestodoscontactsapp.core.util.NetworkConnectivityObserver
import com.rpfcoding.notestodoscontactsapp.core.util.Resource
import com.rpfcoding.notestodoscontactsapp.core.util.getInternetConnectionStatusFlow
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    db: MyDatabase,
    networkConnectivityObserver: NetworkConnectivityObserver,
    private val api: NoteEndpoints,
    private val pref: UserRepository
) : NoteRepository {

    private val noteDao = db.noteDao
    private val networkState = getInternetConnectionStatusFlow(networkConnectivityObserver)

    override suspend fun create(note: Note): Resource<Unit> {
        return try {
            if(networkState.first()) {
                val token = pref.getToken().first()

                val createdId = api.create(
                    token, NoteRequest(
                        title = note.title,
                        description = note.description,
                        color = note.color
                    )
                )

                noteDao.insertOne(note.copy(id = createdId))

                return Resource.Success(Unit)
            }
            Resource.Error("No internet connection.")
        } catch (e: HttpException) {
            Resource.Error(e.response()?.errorBody()?.string() ?: "")
        } catch (e: IOException) {
            Resource.Error("Unknown error occurred.")
        }
    }

    override suspend fun update(note: Note): Resource<Unit> {
        return try {
            if(networkState.first()) {
                val token = pref.getToken().first()

                api.update(
                    token,
                    note.id,
                    NoteRequest(
                        title = note.title,
                        description = note.description,
                        color = note.color
                    )
                )

                noteDao.insertOne(note)

                return Resource.Success(Unit)
            }
            Resource.Error("No internet connection.")
        } catch (e: HttpException) {
            Resource.Error(e.response()?.errorBody()?.string() ?: "")
        } catch (e: IOException) {
            Resource.Error("Unknown error occurred.")
        }
    }

    override suspend fun getAll(): Resource<List<Note>> {
        return try {
            if(networkState.first()) {
                val token = pref.getToken().first()

                val response = api.getAll(token)

                val mappedToNote = response.map { it.toNote() }

                noteDao.insertMany(mappedToNote)
            }

            Resource.Success(noteDao.getAll())
        } catch (e: HttpException) {
            Resource.Error(e.response()?.errorBody()?.string() ?: "")
        } catch (e: IOException) {
            Resource.Error("Unknown error occurred.")
        }
    }

    override suspend fun deleteById(id: String): Resource<Unit> {
        return try {
            if(networkState.first()) {
                val token = pref.getToken().first()

                api.delete(token, id)

                noteDao.deleteById(id)

                return Resource.Success(Unit)
            }
            Resource.Error("No internet connection.")
        } catch (e: HttpException) {
            Resource.Error(e.response()?.errorBody()?.string() ?: "")
        } catch (e: IOException) {
            Resource.Error("Unknown error occurred.")
        }
    }

    override suspend fun deleteAll(): Resource<Unit> {
        return try {
            if(networkState.first()) {
                val token = pref.getToken().first()

                api.deleteAll(token)

                noteDao.deleteAll()

                return Resource.Success(Unit)
            }
            Resource.Error("No internet connection.")
        } catch (e: HttpException) {
            Resource.Error(e.response()?.errorBody()?.string() ?: "")
        } catch (e: IOException) {
            Resource.Error("Unknown error occurred.")
        }
    }
}