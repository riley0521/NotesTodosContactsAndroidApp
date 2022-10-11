package com.rpfcoding.notestodoscontactsapp.auth.presentation.login

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpfcoding.notestodoscontactsapp.R
import com.rpfcoding.notestodoscontactsapp.core.domain.repository.AuthRepository
import com.rpfcoding.notestodoscontactsapp.core.domain.repository.TaskRepository
import com.rpfcoding.notestodoscontactsapp.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val _eventChannel = Channel<UiEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.ClickedForgotPassword -> {
                viewModelScope.launch {
                    // TODO: I have to fix login and sign up first.
                }
            }
            LoginEvent.ClickedSignIn -> {
                onSignInClick()
            }
            is LoginEvent.EnteredEmail -> {
                state = state.copy(email = event.value)
            }
            is LoginEvent.EnteredPassword -> {
                state = state.copy(password = event.value)
            }
        }
    }

    private fun onSignInClick() {
        viewModelScope.launch {

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
                // TODO: Handle login function.
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

    sealed class UiEvent {
        data class ShowMessage(val message: UiText) : UiEvent()
        object NavigateBack : UiEvent()
    }

    sealed class LoginEvent {
        data class EnteredEmail(val value: String) : LoginEvent()
        data class EnteredPassword(val value: String) : LoginEvent()
        object ClickedForgotPassword : LoginEvent()
        object ClickedSignIn : LoginEvent()
    }
}