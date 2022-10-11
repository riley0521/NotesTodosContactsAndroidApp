package com.rpfcoding.notestodoscontactsapp.auth.presentation.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpfcoding.notestodoscontactsapp.core.domain.repository.AuthRepository
import com.rpfcoding.notestodoscontactsapp.core.domain.repository.TaskRepository
import com.rpfcoding.notestodoscontactsapp.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

    var state by mutableStateOf(SettingsState())
        private set

    private val _eventChannel = Channel<UiEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()


    fun onEvent(event: SettingsEvent) {
        when (event) {
            SettingsEvent.ClickedDeleteAccount -> {
                // TODO: Delete account and all it's data (note, task, contacts)
            }
            SettingsEvent.ClickedSignOut -> {
                // TODO: Handle sign out

                viewModelScope.launch {
                    _eventChannel.send(
                        UiEvent.NavigateToHome
                    )
                }
            }
        }
    }

    private suspend fun deleteAccount() {
        // TODO: Delete account and all it's data (note, task, contacts)
    }

    sealed class UiEvent {
        data class ShowMessage(val message: UiText) : UiEvent()
        object NavigateToHome : UiEvent()
    }

    sealed class SettingsEvent {
        object ClickedSignOut : SettingsEvent()
        object ClickedDeleteAccount : SettingsEvent()
    }
}