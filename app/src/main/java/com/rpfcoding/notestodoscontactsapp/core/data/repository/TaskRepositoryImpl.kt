package com.rpfcoding.notestodoscontactsapp.core.data.repository

import com.rpfcoding.notestodoscontactsapp.core.data.local.MyDatabase
import com.rpfcoding.notestodoscontactsapp.core.data.remote.TaskEndpoints
import com.rpfcoding.notestodoscontactsapp.core.data.remote.request.TaskRequest
import com.rpfcoding.notestodoscontactsapp.core.domain.model.Task
import com.rpfcoding.notestodoscontactsapp.core.domain.repository.TaskRepository
import com.rpfcoding.notestodoscontactsapp.core.domain.repository.UserRepository
import com.rpfcoding.notestodoscontactsapp.core.util.NetworkConnectivityObserver
import com.rpfcoding.notestodoscontactsapp.core.util.Resource
import com.rpfcoding.notestodoscontactsapp.core.util.getInternetConnectionStatusFlow
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    db: MyDatabase,
    networkConnectivityObserver: NetworkConnectivityObserver,
    private val api: TaskEndpoints,
    private val repository: UserRepository
) : TaskRepository {

    private val taskDao = db.taskDao
    private val networkState = getInternetConnectionStatusFlow(networkConnectivityObserver)

    override suspend fun getAll(): Resource<List<Task>> {
        return try {
            if(networkState.first()) {
                val token = repository.getToken().first()

                val tasks = api.getAll(token).map { it.toTask() }

                taskDao.insertMany(tasks)
            }

            Resource.Success(taskDao.getAll())
        } catch (e: HttpException) {
            Resource.Error(e.response()?.errorBody()?.string() ?: "")
        } catch (e: IOException) {
            Resource.Error("Unknown error occurred.")
        }
    }

    override suspend fun create(task: Task): Resource<Unit> {
        return try {
            if(networkState.first()) {
                val token = repository.getToken().first()

                val createdTaskId = api.create(
                    token,
                    TaskRequest(
                        title = task.title,
                        priority = task.priority,
                        dueDate = task.dueDate,
                        description = task.description,
                        flag = task.flag,
                        completed = task.completed
                    )
                )

                taskDao.insertOne(task.copy(id = createdTaskId))

                return Resource.Success(Unit)
            }

            Resource.Error("No internet connection.")
        } catch (e: HttpException) {
            Resource.Error(e.response()?.errorBody()?.string() ?: "")
        } catch (e: IOException) {
            Resource.Error("Unknown error occurred.")
        }
    }

    override suspend fun update(task: Task): Resource<Unit> {
        return try {
            if(networkState.first()) {
                val token = repository.getToken().first()

                api.update(
                    token,
                    task.id,
                    TaskRequest(
                        title = task.title,
                        priority = task.priority,
                        dueDate = task.dueDate,
                        description = task.description,
                        flag = task.flag,
                        completed = task.completed
                    )
                )

                taskDao.insertOne(task)

                return Resource.Success(Unit)
            }

            Resource.Error("No internet connection.")
        } catch (e: HttpException) {
            Resource.Error(e.response()?.errorBody()?.string() ?: "")
        } catch (e: IOException) {
            Resource.Error("Unknown error occurred.")
        }
    }

    override suspend fun delete(taskId: String): Resource<Unit> {
        return try {
            if(networkState.first()) {
                val token = repository.getToken().first()

                api.delete(token, taskId)

                taskDao.deleteById(taskId)

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
                val token = repository.getToken().first()

                api.deleteAll(token)

                taskDao.deleteAll()

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