package com.rpfcoding.notestodoscontactsapp.auth.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rpfcoding.notestodoscontactsapp.R
import com.rpfcoding.notestodoscontactsapp.auth.presentation.login.components.DefaultTextField
import com.rpfcoding.notestodoscontactsapp.core.presentation.UiText
import com.rpfcoding.notestodoscontactsapp.destinations.SettingsScreenDestination

@Destination
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val state = viewModel.state

    LoginContent(
        email = state.email,
        emailError = state.emailError,
        password = state.password,
        passwordError = state.passwordError,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onForgotPasswordClick = viewModel::onForgotPasswordClick
    ) {
        navigator.popBackStack()
        navigator.navigate(SettingsScreenDestination)
    }
}

@Composable
fun LoginContent(
    email: String,
    emailError: UiText? = null,
    password: String,
    passwordError: UiText? = null,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSignInClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DefaultTextField(
            value = email,
            onValueChange = onEmailChange,
            hint = stringResource(id = R.string.hint_email),
            leadingIcon = Icons.Filled.Email,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        if (emailError != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = emailError.asString(),
                color = MaterialTheme.colors.error,
                modifier = Modifier.align(Alignment.End)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        DefaultTextField(
            value = password,
            onValueChange = onPasswordChange,
            hint = stringResource(id = R.string.hint_password),
            leadingIcon = Icons.Filled.Lock,
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        if (passwordError != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = passwordError.asString(),
                color = MaterialTheme.colors.error,
                modifier = Modifier.align(Alignment.End)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onSignInClick,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.btn_sign_in))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.forgot_password),
            color = Color.Blue,
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    onForgotPasswordClick()
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginContentPreview() {
    LoginContent(
        email = "",
        password = "",
        onEmailChange = {},
        onPasswordChange = {},
        onForgotPasswordClick = {}
    ) {

    }
}