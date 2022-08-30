package com.rpfcoding.notestodoscontactsapp.auth.presentation.login

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpfcoding.notestodoscontactsapp.R
import com.rpfcoding.notestodoscontactsapp.auth.domain.repository.AuthRepository
import com.rpfcoding.notestodoscontactsapp.auth.domain.repository.LoggerRepository
import com.rpfcoding.notestodoscontactsapp.auth.domain.repository.TaskRepository
import com.rpfcoding.notestodoscontactsapp.core.presentation.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val loggerRepository: LoggerRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val _eventChannel = Channel<UiEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    fun onEmailChange(value: String) {
        state = state.copy(email = value)
    }

    fun onPasswordChange(value: String) {
        state = state.copy(password = value)
    }

    fun onSignInClick(openAndPopUp: () -> Unit) {
        viewModelScope.launch() {
            state = state.copy(isLoading = true)

            verifyEmail()

            state = if (state.password.isBlank()) {
                state.copy(passwordError = UiText.StringResource(resId = R.string.password_not_blank))
            } else {
                state.copy(passwordError = null)
            }

            if (state.emailError == null &&
                state.passwordError == null
            ) {
                val oldUserId = authRepository.getUserId()
                authRepository.authenticate(state.email, state.password) { error ->
                    if (error == null) {
                        linkWithEmail()
                        updateUserId(oldUserId, openAndPopUp)
                    }
                }
            }

            state = state.copy(isLoading = false)
        }
    }

    private fun verifyEmail() {
        state = if (state.email.isBlank()) {
            state.copy(emailError = UiText.StringResource(resId = R.string.email_not_empty))
        } else if (!Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
            state.copy(emailError = UiText.StringResource(resId = R.string.invalid_email))
        } else {
            state.copy(emailError = null)
        }
    }

    private fun updateUserId(oldUserId: String, openAndPopUp: () -> Unit) {
        viewModelScope.launch() {
            val newUserId = authRepository.getUserId()

            taskRepository.updateUserId(oldUserId, newUserId) { error ->
                if (error != null) loggerRepository.logNonFatalCrash(error)
                else openAndPopUp()
            }
        }
    }

    private fun linkWithEmail() {
        viewModelScope.launch {
            authRepository.linkAccount(state.email, state.password) {
                if (it != null) loggerRepository.logNonFatalCrash(it)
            }
        }
    }

    fun onForgotPasswordClick() {
        verifyEmail()

        if (state.emailError == null) {
            authRepository.sendRecoveryEmail(state.email) {
                if (it != null) {
                    viewModelScope.launch {
                        _eventChannel.send(
                            UiEvent.ShowMessage(UiText.DynamicString(it.message ?: ""))
                        )
                    }
                } else {
                    viewModelScope.launch {
                        _eventChannel.send(
                            UiEvent.ShowMessage(UiText.StringResource(resId = R.string.recovery_email_sent))
                        )

                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowMessage(val message: UiText) : UiEvent()
    }
}