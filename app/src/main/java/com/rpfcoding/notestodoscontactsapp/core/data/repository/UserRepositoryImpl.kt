package com.rpfcoding.notestodoscontactsapp.core.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.rpfcoding.notestodoscontactsapp.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val PREFERENCES_NAME = "my_preferences"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class UserRepositoryImpl @Inject constructor(
    ctx: Context
) : UserRepository {

    private object PreferencesKey {
        val tokenKey = stringPreferencesKey(name = "my_token")
    }

    private val dataStore = ctx.dataStore

    override suspend fun saveToken(token: String) {
        dataStore.edit { pref ->
            pref[PreferencesKey.tokenKey] = token
        }
    }

    override fun getToken(): Flow<String> {
        return dataStore
            .data
            .catch {
                emit(emptyPreferences())
            }
            .map { pref ->
                val token = pref[PreferencesKey.tokenKey] ?: ""
                if(token.isNotBlank()) "Bearer $token" else ""
            }
    }
}