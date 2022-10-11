package com.rpfcoding.notestodoscontactsapp.auth.presentation.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rpfcoding.notestodoscontactsapp.R
import com.rpfcoding.notestodoscontactsapp.core.components.DefaultTextField
import com.rpfcoding.notestodoscontactsapp.core.components.BasicAppBar
import com.rpfcoding.notestodoscontactsapp.core.components.DefaultButton
import com.rpfcoding.notestodoscontactsapp.core.util.UiText
import com.rpfcoding.notestodoscontactsapp.destinations.LoginScreenDestination
import com.rpfcoding.notestodoscontactsapp.destinations.SettingsScreenDestination
import kotlinx.coroutines.flow.collectLatest

@Destination
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val state = viewModel.state

    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.eventFlow) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                LoginViewModel.UiEvent.NavigateBack -> {
                    navigator.popBackStack(LoginScreenDestination.route, true)
                    navigator.navigate(SettingsScreenDestination)
                }
                is LoginViewModel.UiEvent.ShowMessage -> {
                    Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    BasicAppBar(title = R.string.sign_in)

    LoginContent(
        isLoading = state.isLoading,
        email = state.email,
        emailError = state.emailError,
        password = state.password,
        passwordError = state.passwordError,
        onEmailChange = {
            viewModel.onEvent(LoginViewModel.LoginEvent.EnteredEmail(it))
        },
        onPasswordChange = {
            viewModel.onEvent(LoginViewModel.LoginEvent.EnteredPassword(it))
        },
        onForgotPasswordClick = {
            viewModel.onEvent(LoginViewModel.LoginEvent.ClickedForgotPassword)
        },
        onSignInClick = {
            viewModel.onEvent(LoginViewModel.LoginEvent.ClickedSignIn)
        }
    )
}

@Composable
fun LoginContent(
    isLoading: Boolean,
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
            .padding(16.dp)
            .fillMaxSize()
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
        DefaultButton(
            title = R.string.btn_sign_in,
            action = onSignInClick,
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.forgot_password),
            color = Color.White,
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
        isLoading = false,
        email = "",
        emailError = UiText.StringResource(resId = R.string.unknown_error),
        password = "",
        passwordError = UiText.StringResource(resId = R.string.unknown_error),
        onEmailChange = {},
        onPasswordChange = {},
        onForgotPasswordClick = {},
        onSignInClick = {}
    )
}