package com.rpfcoding.notestodoscontactsapp.core.data.repository

import com.rpfcoding.notestodoscontactsapp.core.data.remote.AuthEndpoints
import com.rpfcoding.notestodoscontactsapp.core.data.remote.request.AuthRequest
import com.rpfcoding.notestodoscontactsapp.core.domain.repository.AuthRepository
import com.rpfcoding.notestodoscontactsapp.core.domain.repository.UserRepository
import com.rpfcoding.notestodoscontactsapp.core.util.NetworkConnectivityObserver
import com.rpfcoding.notestodoscontactsapp.core.util.Resource
import com.rpfcoding.notestodoscontactsapp.core.util.getInternetConnectionStatusFlow
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    networkConnectivityObserver: NetworkConnectivityObserver,
    private val userRepository: UserRepository,
    private val api: AuthEndpoints
) : AuthRepository {

    private val networkState = getInternetConnectionStatusFlow(networkConnectivityObserver)

    override suspend fun login(email: String, password: String): Resource<Unit> {
        return try {
            if(networkState.first()) {
                val token = api.login(AuthRequest(email, password))

                userRepository.saveToken(token)

                return Resource.Success(Unit)
            }

            Resource.Error("No internet connection.")
        } catch (e: HttpException) {
            Resource.Error(e.response()?.errorBody()?.string() ?: "")
        } catch (e: IOException) {
            Resource.Error("Unknown error occurred.")
        }
    }

    override suspend fun signup(email: String, password: String): Resource<Unit> {
        return try {
            if(networkState.first()) {
                api.signUp(AuthRequest(email, password))

                return Resource.Success(Unit)
            }

            Resource.Error("No internet connection.")
        } catch (e: HttpException) {
            Resource.Error(e.response()?.errorBody()?.string() ?: "")
        } catch (e: IOException) {
            Resource.Error("Unknown error occurred.")
        }
    }

    override suspend fun authenticate(): Resource<Unit> {
        return try {
            if(networkState.first()) {
                val token = userRepository.getToken().first()

                api.authenticate(token)

                return Resource.Success(Unit)
            }

            Resource.Error("No internet connection.")
        } catch (e: HttpException) {
            Resource.Error(e.response()?.errorBody()?.string() ?: "")
        } catch (e: IOException) {
            Resource.Error("Unknown error occurred.")
        }
    }

    override suspend fun signOut(): Resource<Unit> {
        userRepository.saveToken("")

        return Resource.Success(Unit)
    }
}