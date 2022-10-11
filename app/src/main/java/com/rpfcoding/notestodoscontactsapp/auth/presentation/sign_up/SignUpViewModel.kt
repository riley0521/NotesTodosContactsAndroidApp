package com.rpfcoding.notestodoscontactsapp.auth.presentation.sign_up

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
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val taskRepository: TaskRepository,
) : ViewModel() {

    var state by mutableStateOf(SignUpState())
        private set

    private val _eventChannel = Channel<UiEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            SignUpEvent.ClickedSignUp -> {
                onSignUpClick()
            }
            is SignUpEvent.EnteredConfirmPassword -> {
                state = state.copy(confirmPass = event.value)
            }
            is SignUpEvent.EnteredEmail -> {
                state = state.copy(email = event.value)
            }
            is SignUpEvent.EnteredPassword -> {
                state = state.copy(password = event.value)
            }
        }
    }

    private fun onSignUpClick() {
        state = state.copy(isLoading = true)

        validateEmail()

        state = if (state.password.isBlank()) {
            state.copy(passwordError = UiText.StringResource(resId = R.string.password_not_blank))
        } else {
            state.copy(passwordError = null)
        }

        state = if (state.confirmPass.isBlank()) {
            state.copy(confirmPassError = UiText.StringResource(resId = R.string.confirm_password_not_blank))
        } else if (state.password != state.confirmPass) {
            state.copy(confirmPassError = UiText.StringResource(resId = R.string.password_not_match))
        } else {
            state.copy(confirmPassError = null)
        }

        if (
            state.emailError == null &&
            state.passwordError == null &&
            state.confirmPassError == null
        ) {
            viewModelScope.launch {
                // TODO: Handle sign up
            }
        }

        state = state.copy(isLoading = false)
    }

    private fun validateEmail() {
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

    sealed class SignUpEvent {
        data class EnteredEmail(val value: String) : SignUpEvent()
        data class EnteredPassword(val value: String) : SignUpEvent()
        data class EnteredConfirmPassword(val value: String) : SignUpEvent()
        object ClickedSignUp : SignUpEvent()
    }
}