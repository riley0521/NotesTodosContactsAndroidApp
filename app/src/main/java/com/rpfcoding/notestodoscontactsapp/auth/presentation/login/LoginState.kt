package com.rpfcoding.notestodoscontactsapp.auth.presentation.login

import com.rpfcoding.notestodoscontactsapp.core.util.UiText

data class LoginState(
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val isLoading: Boolean = false,
    val error: UiText? = null
)
