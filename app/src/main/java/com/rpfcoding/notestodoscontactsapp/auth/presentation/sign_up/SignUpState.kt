package com.rpfcoding.notestodoscontactsapp.auth.presentation.sign_up

import com.rpfcoding.notestodoscontactsapp.core.util.UiText

data class SignUpState(
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val confirmPass: String = "",
    val confirmPassError: UiText? = null,
    val isLoading: Boolean = false,
    val error: UiText? = null
)
